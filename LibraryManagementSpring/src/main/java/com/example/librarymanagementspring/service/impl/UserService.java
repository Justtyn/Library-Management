package com.example.librarymanagementspring.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.User;
import com.example.librarymanagementspring.mapper.UserMapper;
import com.example.librarymanagementspring.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {

    private static final String DEFAULT_PASS = "1234";
    private static final String PASS_SALT = "jiaozihao";

    @Autowired
    UserMapper userMapper;

    // 获取全部用户数据接口
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    // 获取全部用户数据接口 json 格式 倒序 (默认)
    @Override
    public PageInfo<User> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByCondition(baseRequest);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }

    // 保存添加用户接口
    @Override
    public void save(User user) {
        // 默认密码
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword(DEFAULT_PASS);
        }
        // 密码加密 md5 加盐
        user.setPassword(securePass(user.getPassword() + PASS_SALT));
        Date date = new Date();
        // username 当作 UUID 来处理
        user.setUsername(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        userMapper.save(user);
    }

    // 查询单个用户数据接口
    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    // 提交更新用户数据接口
    @Override
    public void update(User user) {
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    // 删除用户数据接口
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    // 注册用户 name 参数查重
    @Override
    public List<User> nameCheck(String name) {
        return userMapper.nameCheckByName(name);
    }

    // 正序查询所有用户接口
    @Override
    public PageInfo<User> pageAsc(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByConditionAsc(baseRequest);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }

    private String securePass(String password) {
        return SecureUtil.md5(password + PASS_SALT);
    }

}

package com.example.librarymanagementspring.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.librarymanagementspring.common.Result;
import com.example.librarymanagementspring.controller.DTO.AdminLoginDTO;
import com.example.librarymanagementspring.controller.request.AdminLoginRequest;
import com.example.librarymanagementspring.controller.request.AdminPasswordChangeRequest;
import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Admin;
import com.example.librarymanagementspring.entity.User;
import com.example.librarymanagementspring.exception.ServiceException;
import com.example.librarymanagementspring.mapper.AdminMapper;
import com.example.librarymanagementspring.mapper.UserMapper;
import com.example.librarymanagementspring.service.IAdminService;
import com.example.librarymanagementspring.service.IUserService;
import com.example.librarymanagementspring.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.rowset.serial.SerialException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {

    private static final String DEFAULT_PASS = "1234";
    private static final String PASS_SALT = "jiaozihao";

    @Autowired
    AdminMapper adminMapper;
    private AdminLoginDTO adminLoginDTO;

    // 获取全部用户数据接口
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    // 获取全部用户数据接口 json 格式
    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
        return adminPageInfo;
    }

    // 保存添加管理员接口
    @Override
    public void save(Admin admin) {
        // 默认密码
        if (StrUtil.isBlank(admin.getPassword())) {
            admin.setPassword(DEFAULT_PASS);
        }
        // 密码加密 md5 加盐
        admin.setPassword(securePass(admin.getPassword() + PASS_SALT));
//        Date date = new Date();
        // 当作卡号来处理
//        admin.setUsername(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        adminMapper.save(admin);
    }

    // 查询单个用户数据接口
    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    // 提交更新用户数据接口
    @Override
    public void update(Admin admin) {
        admin.setUpdatetime(new Date());
        adminMapper.updateById(admin);
    }

    // 删除用户数据接口
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    // 注册用户 name 参数查重
    @Override
    public List<Admin> nameCheck(String username) {
        return adminMapper.nameCheckByName(username);
    }

    @Override
    public PageInfo<Admin> pageAsc(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByConditionAsc(baseRequest);
        PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
        return adminPageInfo;
    }

    // 管理员登陆接口
    @Override
    public AdminLoginDTO login(AdminLoginRequest request) {
        String password = request.getPassword();
        request.setPassword(securePass(password));
        Admin admin = adminMapper.getByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }
        if (!admin.isStatus()) {
            throw new ServiceException("当前用户已被禁用 请联系管理员");
        }
        // 将admin 对象转换为 DTO 对象
        AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
        BeanUtils.copyProperties(admin, adminLoginDTO);

        // 生成 token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword());

        adminLoginDTO.setToken(token);
        return adminLoginDTO;
    }

    @Override
    public void register(Admin obj) {
        // 默认密码
        if (StrUtil.isBlank(obj.getPassword())) {
            obj.setPassword(DEFAULT_PASS);
        }
        // 密码加密 md5 加盐
        obj.setPassword(securePass(obj.getPassword() + PASS_SALT));
        adminMapper.register(obj);
    }

    @Override
    public void changePass(AdminPasswordChangeRequest request) {

        // 对新的密码进行加密
        String newPass = request.getNewPass();
        request.setNewPass(securePass(newPass));
        int count = adminMapper.updatePassword(request);
        if (count <= 0 ) {
            throw new ServiceException("修改失败");
        }
    }

    private String securePass(String password) {
        return SecureUtil.md5(password + PASS_SALT);
    }
}

package com.example.librarymanagementspring.service.impl;

import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Category;
import com.example.librarymanagementspring.mapper.CategoryMapper;
import com.example.librarymanagementspring.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;
//    private AdminLoginDTO adminLoginDTO;

    // 获取全部分类数据接口
    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    // 获取全部分类数据接口 json 格式 倒序 默认
    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        // 自关联查询
        List<Category> categories = categoryMapper.listByCondition(baseRequest);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categories);
        return categoryPageInfo;
    }

    // 保存添加分类数据接口
    @Override
    public void save(Category category) {
        categoryMapper.save(category);
    }

    // 通过 ID 查询单条分类数据接口
    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    // 提交更新分类数据接口
    @Override
    public void update(Category category) {
        category.setUpdateTime(new Date());
        categoryMapper.updateById(category);
    }

    // 删除分类数据接口
    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    // 获取全部分类数据接口 json 格式 正序
    @Override
    public PageInfo<Category> pageAsc(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Category> categories = categoryMapper.listByConditionAsc(baseRequest);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categories);
        return categoryPageInfo;
    }

    // 添加分类 name 参数查重
    @Override
    public List<Category> nameCheck(String name) {
        return categoryMapper.nameCheckByName(name);
    }
}

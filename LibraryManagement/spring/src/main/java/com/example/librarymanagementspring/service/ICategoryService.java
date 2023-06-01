package com.example.librarymanagementspring.service;

import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICategoryService {
    List<Category> list();

    PageInfo<Category> page(BaseRequest BaseRequest);

    void save(Category obj);

    Category getById(Integer id);

    void update(Category obj);

    void deleteById(Integer id);

    Object pageAsc(BaseRequest BaseRequest);

    List<Category> nameCheck(String name);
}

package com.example.librarymanagementspring.mapper;

import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> list();

    List<Category> listByCondition(BaseRequest baseRequest);

    void save(Category category);

    Category getById(Integer id);

    void updateById(Category category);

    void deleteById(Integer id);

    List<Category> listByConditionAsc(BaseRequest baseRequest);

    List<Category> nameCheckByName(String name);
}
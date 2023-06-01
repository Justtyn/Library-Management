package com.example.librarymanagementspring.mapper;

import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.controller.request.UserPageRequest;
import com.example.librarymanagementspring.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> list();

    List<User> listByCondition(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id);

    void updateById(User user);

    void deleteById(Integer id);

    List<User> nameCheckByName(String name);

    List<User> listByConditionAsc(BaseRequest baseRequest);
}

package com.example.librarymanagementspring.service;

import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.controller.request.UserPageRequest;
import com.example.librarymanagementspring.entity.User;

import java.util.List;

public interface IUserService {
    List<User> list();

    Object page(BaseRequest BaseRequest);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);

    List<User> nameCheck(String name);

    Object pageAsc(BaseRequest BaseRequest);
}

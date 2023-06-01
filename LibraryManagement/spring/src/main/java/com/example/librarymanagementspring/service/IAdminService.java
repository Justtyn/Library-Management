package com.example.librarymanagementspring.service;

import com.example.librarymanagementspring.controller.DTO.AdminLoginDTO;
import com.example.librarymanagementspring.controller.request.AdminLoginRequest;
import com.example.librarymanagementspring.controller.request.AdminPasswordChangeRequest;
import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IAdminService {
    List<Admin> list();

    PageInfo<Admin> page(BaseRequest BaseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void update(Admin obj);

    void deleteById(Integer id);

    List<Admin> nameCheck(String username);

    Object pageAsc(BaseRequest BaseRequest);

    AdminLoginDTO login(AdminLoginRequest request);

    void register(Admin obj);

    void changePass(AdminPasswordChangeRequest request);
}

package com.example.librarymanagementspring.mapper;

import com.example.librarymanagementspring.controller.request.AdminPasswordChangeRequest;
import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<Admin> list();

    List<Admin> listByCondition(BaseRequest baseRequest);

    void save(Admin admin);

    Admin getById(Integer id);

    void updateById(Admin admin);

    void deleteById(Integer id);

    List<Admin> nameCheckByName(String username);

    List<Admin> listByConditionAsc(BaseRequest baseRequest);

    Admin getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    void register(Admin obj);

    int updatePassword(AdminPasswordChangeRequest request);
}

package com.example.librarymanagementspring.controller;

import com.example.librarymanagementspring.common.Result;
import com.example.librarymanagementspring.controller.DTO.AdminLoginDTO;
import com.example.librarymanagementspring.controller.request.AdminLoginRequest;
import com.example.librarymanagementspring.controller.request.AdminPageRequest;
import com.example.librarymanagementspring.controller.request.AdminPasswordChangeRequest;
import com.example.librarymanagementspring.entity.Admin;
import com.example.librarymanagementspring.service.IAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService iAdminService;

    // 获取全部用户数据接口
    @GetMapping("/list")
    public Result list() {
        List<Admin> admins = iAdminService.list();
        return Result.success(admins);
    }

    // 获取全部用户数据接口 json 格式 倒序（默认）
    @GetMapping("/page")
    public Result page(AdminPageRequest adminPageRequest) {
        return Result.success(iAdminService.page(adminPageRequest));
    }

    // 获取全部用户数据接口 json 格式 正序（默认）
    @GetMapping("/pageAsc")
    public Result pageAsc(AdminPageRequest adminPageRequest) {
        return Result.success(iAdminService.pageAsc(adminPageRequest));
    }

    // 查询单个用户数据接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Admin admin = iAdminService.getById(id);
        return Result.success(admin);
    }

    // 添加用户保存接口
    @PostMapping("/save")
    public Result save(@RequestBody Admin admin) {
        iAdminService.save(admin);
        return Result.success();
    }

    // 提交更新用户数据接口
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        iAdminService.update(admin);
        return Result.success();
    }

    // 修改管理员密码接口
    @PutMapping("/changePass")
    public Result changePass(@RequestBody AdminPasswordChangeRequest request) {
        iAdminService.changePass(request);
        return Result.success();
    }

    // 删除用户数据接口
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        iAdminService.deleteById(id);
        return Result.success();
    }

    // 注册用户 name 参数查重
    @GetMapping("/namecheck")
    public Result nameCheck(@RequestParam String username) {
        List<Admin> admins = iAdminService.nameCheck(username);
        return Result.success(admins);
    }

    // 管理员账号登陆接口
    @PostMapping("login")
    public Result login(@RequestBody AdminLoginRequest request) {

        AdminLoginDTO adminLoginDTO = iAdminService.login(request);
//        if (adminLoginDTO == null) {
//            return Result.error("用户名或密码错误");
//        }
        return Result.success(adminLoginDTO);
    }

    // 添加管理员注册接口 与 save接口一致 请求不需要 token
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        iAdminService.register(admin);
        return Result.success();
    }
}

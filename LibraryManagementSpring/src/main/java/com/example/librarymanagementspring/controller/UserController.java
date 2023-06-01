package com.example.librarymanagementspring.controller;

import com.example.librarymanagementspring.common.Result;
import com.example.librarymanagementspring.controller.request.UserPageRequest;
import com.example.librarymanagementspring.entity.User;
import com.example.librarymanagementspring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    // 获取全部用户数据接口
    @GetMapping("/list")
    public Result list() {
        List<User> users = iUserService.list();
        return Result.success(users);
    }

    // 获取全部用户数据接口 json 格式 倒序（默认）
    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(iUserService.page(userPageRequest));
    }

    // 获取全部用户数据接口 json 格式 正序（默认）
    @GetMapping("/pageAsc")
    public Result pageAsc(UserPageRequest userPageRequest) {
        return Result.success(iUserService.pageAsc(userPageRequest));
    }

    // 查询单个用户数据接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        User user = iUserService.getById(id);
        return Result.success(user);
    }

    // 添加用户保存接口
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        iUserService.save(user);
        return Result.success();
    }

    // 提交更新用户数据接口
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        iUserService.update(user);
        return Result.success();
    }

    // 删除用户数据接口
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        iUserService.deleteById(id);
        return Result.success();
    }

    // 注册用户 name 参数查重
    @GetMapping("/namecheck")
    public Result nameCheck(@RequestParam String name) {
        List<User> users = iUserService.nameCheck(name);
        return Result.success(users);
    }
}

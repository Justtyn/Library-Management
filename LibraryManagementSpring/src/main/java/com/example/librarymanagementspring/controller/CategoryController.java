package com.example.librarymanagementspring.controller;

import com.example.librarymanagementspring.common.Result;
import com.example.librarymanagementspring.controller.request.CategoryPageRequest;
import com.example.librarymanagementspring.entity.Admin;
import com.example.librarymanagementspring.entity.Category;
import com.example.librarymanagementspring.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;

    // 获取全部分类数据接口
    @GetMapping("/list")
    public Result list() {
        List<Category> categories = iCategoryService.list();
        return Result.success(categories);
    }

    // 获取全部分类数据接口 json 格式 倒序（默认）
    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest) {
        return Result.success(iCategoryService.page(categoryPageRequest));
    }

    // 获取全部分类数据接口 json 格式 正序
    @GetMapping("/pageAsc")
    public Result pageAsc(CategoryPageRequest categoryPageRequest) {
        return Result.success(iCategoryService.pageAsc(categoryPageRequest));
    }

    // 查询单个分类数据接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Category category = iCategoryService.getById(id);
        return Result.success(category);
    }

    // 添加分类保存接口
    @PostMapping("/save")
    public Result save(@RequestBody Category category) {
        iCategoryService.save(category);
        return Result.success();
    }

    // 提交更新分类数据接口
    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        iCategoryService.update(category);
        return Result.success();
    }

    // 删除分类数据接口
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        iCategoryService.deleteById(id);
        return Result.success();
    }

    // 添加分类 name 参数查重
    @GetMapping("/namecheck")
    public Result nameCheck(@RequestParam String name) {
        List<Category> categories = iCategoryService.nameCheck(name);
        return Result.success(categories);
    }
}

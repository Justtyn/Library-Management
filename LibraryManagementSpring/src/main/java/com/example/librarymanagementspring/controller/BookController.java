package com.example.librarymanagementspring.controller;

import com.example.librarymanagementspring.common.Result;
import com.example.librarymanagementspring.controller.request.BookPageRequest;
import com.example.librarymanagementspring.controller.request.CategoryPageRequest;
import com.example.librarymanagementspring.entity.Book;
import com.example.librarymanagementspring.entity.Category;
import com.example.librarymanagementspring.service.IBookService;
import com.example.librarymanagementspring.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    IBookService iBookService;

    // 获取全部分类数据接口
    @GetMapping("/list")
    public Result list() {
        List<Book> books = iBookService.list();
        return Result.success(books);
    }

    // 获取全部分类数据接口 json 格式 倒序（默认）
    @GetMapping("/page")
    public Result page(BookPageRequest bookPageRequest) {
        return Result.success(iBookService.page(bookPageRequest));
    }

    // 获取全部分类数据接口 json 格式 正序
    @GetMapping("/pageAsc")
    public Result pageAsc(BookPageRequest bookPageRequest) {
        return Result.success(iBookService.pageAsc(bookPageRequest));
    }

    // 查询单个分类数据接口
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = iBookService.getById(id);
        return Result.success(book);
    }

    // 添加分类保存接口
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {
        iBookService.save(book);
        return Result.success();
    }

    // 提交更新分类数据接口
    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        iBookService.update(book);
        return Result.success();
    }

    // 删除分类数据接口
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        iBookService.deleteById(id);
        return Result.success();
    }

}

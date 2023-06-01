package com.example.librarymanagementspring.service.impl;

import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Book;
import com.example.librarymanagementspring.entity.Category;
import com.example.librarymanagementspring.mapper.BookMapper;
import com.example.librarymanagementspring.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BookService implements IBookService {

    @Autowired
    BookMapper bookMapper;

    // 获取全部分类数据接口
    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    // 获取全部分类数据接口 json 格式 倒序 默认
    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        // 自关联查询
        List<Book> books = bookMapper.listByCondition(baseRequest);
        PageInfo<Book> bookPageInfo = new PageInfo<>(books);
        return bookPageInfo;
    }

    // 保存添加分类数据接口
    @Override
    public void save(Book book) {
        bookMapper.save(book);
    }

    // 通过 ID 查询单条分类数据接口
    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    // 提交更新分类数据接口
    @Override
    public void update(Book book) {
        book.setUpdate_time(new Date());
        bookMapper.updateById(book);
    }

    // 删除分类数据接口
    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    // 获取全部分类数据接口 json 格式 正序
    @Override
    public PageInfo<Book> pageAsc(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Book> books = bookMapper.listByConditionAsc(baseRequest);
        PageInfo<Book> bookPageInfo = new PageInfo<>(books);
        return bookPageInfo;
    }
}

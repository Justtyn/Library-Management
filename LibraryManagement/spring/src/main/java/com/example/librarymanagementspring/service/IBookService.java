package com.example.librarymanagementspring.service;

import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Book;
import com.example.librarymanagementspring.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBookService {
    List<Book> list();

    PageInfo<Book> page(BaseRequest BaseRequest);

    void save(Book obj);

    Book getById(Integer id);

    void update(Book obj);

    void deleteById(Integer id);

    Object pageAsc(BaseRequest BaseRequest);

}

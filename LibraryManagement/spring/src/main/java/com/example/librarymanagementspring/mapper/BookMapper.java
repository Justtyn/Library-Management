package com.example.librarymanagementspring.mapper;

import com.example.librarymanagementspring.controller.request.BaseRequest;
import com.example.librarymanagementspring.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> list();

    List<Book> listByCondition(BaseRequest baseRequest);

    void save(Book book);

    Book getById(Integer id);

    void updateById(Book book);

    void deleteById(Integer id);

    List<Book> listByConditionAsc(BaseRequest baseRequest);

}
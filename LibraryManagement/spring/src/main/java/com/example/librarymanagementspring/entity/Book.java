package com.example.librarymanagementspring.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Book {
    private int id;
    private String name;
    private String description;
    private String publish_date;
    private String author;
    private String press;
    private String category;
    private String isbn;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date create_time;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date update_time;
    private String cover;
}

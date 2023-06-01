package com.example.librarymanagementspring.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data
public class Category {
    private Integer id;
    private String name;
    private String remark;
    private Integer pid;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

    private List<Category> children;
}

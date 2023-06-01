package com.example.librarymanagementspring.controller.request;

import lombok.Data;

@Data
public class CategoryPageRequest extends BaseRequest {
    private Integer id;
    private String name;
}

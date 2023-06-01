package com.example.librarymanagementspring.controller.request;

import lombok.Data;

@Data
public class BookPageRequest extends BaseRequest {
    private Integer id;
    private Integer name;
    private String isbn;
}

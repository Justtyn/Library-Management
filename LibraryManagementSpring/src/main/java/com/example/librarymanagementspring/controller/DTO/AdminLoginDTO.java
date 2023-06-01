package com.example.librarymanagementspring.controller.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AdminLoginDTO {
    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String token;
}

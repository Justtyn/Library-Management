package com.example.librarymanagementspring.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AdminLoginRequest {
    private String username;
    private String password;
}

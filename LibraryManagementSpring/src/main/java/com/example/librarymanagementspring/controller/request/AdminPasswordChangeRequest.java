package com.example.librarymanagementspring.controller.request;

import lombok.Data;

@Data
public class AdminPasswordChangeRequest {
    private String username;
    private String password;
    private String newPass;
}

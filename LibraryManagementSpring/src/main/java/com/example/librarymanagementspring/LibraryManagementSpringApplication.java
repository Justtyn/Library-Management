package com.example.librarymanagementspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class LibraryManagementSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSpringApplication.class, args);
    }

    // 健康检查接口
    @GetMapping
    public String health() {
        return "SUCCESS";
    }

}

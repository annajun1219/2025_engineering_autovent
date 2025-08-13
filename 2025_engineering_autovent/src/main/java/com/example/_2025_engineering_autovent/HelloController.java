package com.example._2025_engineering_autovent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST API 컨트롤러임을 명시
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, API Server!";
    }
}
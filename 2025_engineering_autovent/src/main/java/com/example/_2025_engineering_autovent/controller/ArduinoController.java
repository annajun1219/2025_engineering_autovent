package com.example._2025_engineering_autovent.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ArduinoController {

    // 아두이노에서 전송한 문자열 받기
    @PostMapping(value = "/arduino", consumes = "text/plain")
    public String receiveFromArduino(@RequestBody String message) {
        System.out.println("📩 아두이노로부터 받은 메시지: " + message);
        return "메시지 수신 완료: " + message;
    }
}


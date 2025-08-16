package com.example._2025_engineering_autovent.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/battery")
public class BatteryController {

    // 메모리에 최신 값 저장 (단일 디바이스일 경우)
    private volatile int latestBatteryLevel = 28;


    // 아두이노가 배터리 잔량 전송
    @PostMapping(consumes = "application/json", produces = "application/json")
    public String updateBattery(@RequestParam int level) {
        latestBatteryLevel = level;
        System.out.println("배터리 업데이트: " + level + "%");
        return "Battery level updated: " + level + "%";
    }

    // 앱이 배터리 잔량 조회
    @GetMapping
    public int getBatteryLevel() {
        return latestBatteryLevel;
    }
}

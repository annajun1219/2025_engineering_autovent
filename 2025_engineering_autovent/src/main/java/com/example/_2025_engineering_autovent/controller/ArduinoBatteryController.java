package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.ArduinoBatteryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/battery")
public class ArduinoBatteryController {

    // 최신 배터리 잔량 저장 (단일 디바이스 기준)
    private volatile int latestBatteryLevel = 0;
    private volatile int latestLocationId = 0;
    private volatile String latestWindowId = "";

    // 아두이노가 배터리 잔량을 JSON으로 전송할 때
    @PostMapping(value = "arduino", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateBattery(@RequestBody ArduinoBatteryRequest request) {
        latestLocationId = request.getLocation_id();
        latestWindowId = request.getWindowId();
        latestBatteryLevel = request.getLevel();

        System.out.println("location_id: "+ latestLocationId+ " window_id: " + latestWindowId+" 배터리 레벨 업데이트: " + latestBatteryLevel + "%");

        return ResponseEntity.ok().body(
                new ResponseMessage("success", "Battery level updated", latestLocationId, latestWindowId, latestBatteryLevel)
        );
    }

    // 앱이 배터리 잔량 조회(GET)
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getBatteryLevel() {
        return ResponseEntity.ok().body(
                new BatteryResponse(latestLocationId, latestWindowId, latestBatteryLevel)
        );
    }

    // 응답 메시지 클래스
    static class ResponseMessage {
        private String status;
        private String message;
        private int location_id;
        private String window_id;
        private int level;

        public ResponseMessage(String status, String message, int location_id, String window_id,int level) {
            this.status = status;
            this.message = message;
            this.location_id = location_id;
            this.window_id = window_id;
            this.level = level;
        }

        public String getStatus() { return status; }
        public String getMessage() { return message; }
        public int getLocation_id() { return location_id; }
        public String getWindow_id() { return window_id; }
        public int getLevel() { return level; }
    }

    // 배터리 레벨 응답 클래스
    static class BatteryResponse {
        private int location_id;
        private String window_id;
        private int level;

        public BatteryResponse( int location_id, String window_id,int level) {
            this.location_id = location_id;
            this.window_id = window_id;
            this.level = level;
        }
        public int getLocation_id() { return location_id; }
        public String getWindow_id() { return window_id; }
        public int getLevel() { return level; }
    }
}

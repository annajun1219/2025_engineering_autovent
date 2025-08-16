package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.ArduinoBatteryRequest;
import com.example._2025_engineering_autovent.dto.ArduinoRainRequest;
import com.example._2025_engineering_autovent.dto.DustRequest;
import com.example._2025_engineering_autovent.dto.RainRequest;
import com.example._2025_engineering_autovent.entity.windows;
import com.example._2025_engineering_autovent.repository.LocationRepository;
import com.example._2025_engineering_autovent.repository.WindowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/rain")
public class ArduinoRainController {
    @Autowired
    private WindowsRepository windowsRepository;
    // 최신 배터리 잔량 저장 (단일 디바이스 기준)
    private volatile int latestRainLevel = 0;
    private volatile int latestLocationId = 0;
    private volatile String latestWindowId = "";



    // 아두이노가 배터리 잔량을 JSON으로 전송할 때
    @PostMapping(value = "arduino", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateBattery(@RequestBody ArduinoRainRequest request) {
        latestLocationId = request.getLocation_id();
        latestWindowId = request.getWindowId();
        latestRainLevel = request.getLevel();



        System.out.println("location_id: " + latestLocationId + "window_id: " + latestWindowId+" 배터리 레벨 업데이트: " + latestRainLevel + "%");

        if (latestRainLevel > 200){ //200미만일 시에는 우천모드
            sendRainModeRequest();
        }
        return ResponseEntity.ok().body(
                new ResponseMessage("success", "Battery level updated", latestLocationId, latestWindowId, latestRainLevel)
        );


    }

    // 앱이 배터리 잔량 조회(GET)
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getRainLevel() {
        return ResponseEntity.ok().body(
                new ArduinoRainResponse(latestLocationId, latestWindowId, latestRainLevel)
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

        public int getLocation_id() {return location_id;}
        public String getStatus() { return status; }
        public String getMessage() { return message; }
        public String getWindow_id() { return window_id; }
        public int getLevel() { return level; }
    }

    // 배터리 레벨 응답 클래스
    static class ArduinoRainResponse {
        private int location_id;
        private String window_id;
        private int level;

        public ArduinoRainResponse(int location_id, String window_id,int level) {
            this.location_id = location_id;
            this.window_id = window_id;
            this.level = level;
        }
        public int getLocation_id() { return location_id; }
        public String getWindow_id() { return window_id; }
        public int getLevel() { return level; }
    }

    private void sendRainModeRequest() {
        RainRequest rainRequest = new RainRequest();
        rainRequest.setLocation_id(latestLocationId); // 실제 location_id
        rainRequest.setWindow_id(latestWindowId);   // 실제 window_id
        rainRequest.setMode("rain");   // 미세먼지 모드
        rainRequest.setTime_stamp(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }


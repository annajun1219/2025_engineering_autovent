package com.example._2025_engineering_autovent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fire")
public class ArduinoFireController {

    // (필수) FCM 등 Push 서비스 주입
    @Autowired
    private PushNotificationService pushNotificationService;

    @PostMapping(value = "arduino", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> receiveFireSensorData(@RequestBody FireSensorRequest request) {
        // 예시 request: location_id, window_id, fire_value, time_stamp

        System.out.println("화재 센서값 수신, window_id: " + request.getWindowId() + ", fire: " + request.getFire());

        // 1. 센서 데이터 DB 저장/처리 등...

        // 2. 화재 감지(값이 오기만 하면 알림 필요 시)
        pushNotificationService.sendFireAlertToApp(request);

        // 3. 응답 반환
        return ResponseEntity.ok().body(
                new FireSensorResponse(200, true, "🔥 화재 센서값 수신, 알림 생성 완료",
                        request.getLocationId(), request.getWindowId(), request.getFire(), request.getTimeStamp())
        );
    }

    // 요청 DTO
    public static class FireSensorRequest {
        private int location_id;
        private String window_id;
        private String fire;    // 값의 타입(예: "fire_1" 등) 상황에 맞게 수정
        private String time_stamp;

        // getter, setter 등...
    }

    // 응답 DTO (API명세 참고)
    public static class FireSensorResponse {
        private int status;
        private boolean success;
        private String message;
        private int location_id;
        private String window_id;
        private String fire;
        private String time_stamp;

        public FireSensorResponse(int status, boolean success, String message,
                                  int location_id, String window_id, String fire, String time_stamp) {
            this.status = status;
            this.success = success;
            this.message = message;
            this.location_id = location_id;
            this.window_id = window_id;
            this.fire = fire;
            this.time_stamp = time_stamp;
        }
        // getter 등...
    }
}

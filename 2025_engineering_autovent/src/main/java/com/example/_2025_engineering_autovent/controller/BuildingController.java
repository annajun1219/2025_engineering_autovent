package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.entity.location;
import com.example._2025_engineering_autovent.entity.windows;
import com.example._2025_engineering_autovent.repository.LocationRepository;
import com.example._2025_engineering_autovent.repository.WindowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/setting")
public class BuildingController {

    @Autowired
    private  WindowsRepository windowsRepository;
    @Autowired
    private  LocationRepository locationRepository;

    // 생성자 주입 등 생략

    @GetMapping("/building")
    public ResponseEntity<?> getUserWindows(@RequestParam("email") String email) {
        try {
            Optional<windows> optionalWindowsList = windowsRepository.findFirstByUserEmail(email);

            if (optionalWindowsList.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", 404);
                response.put("success", false);
                response.put("message", "해당 이메일로 등록된 창문 데이터가 없습니다.");
                response.put("data", Collections.emptyList());
                return ResponseEntity.status(404).body(response);
            }

            windows window = optionalWindowsList.get();
            location loc = window.getLocation();

            List<Map<String, Object>> result = new ArrayList<>();
            if (loc != null) {
                Map<String, Object> windowInfo = new LinkedHashMap<>();
                windowInfo.put("location", loc.getLocation());
                windowInfo.put("floor", loc.getFloor());
                windowInfo.put("room_num", loc.getRoomNum());
                windowInfo.put("window_id", window.getIdWindows());
                windowInfo.put("on_off", "on");
                result.add(windowInfo);
            }


            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("success", true);
            response.put("message", "창문 및 건물 정보 조회 성공");
            response.put("data", result);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 500);
            response.put("success", false);
            response.put("message", "서버 오류");
            return ResponseEntity.status(500).body(response);
        }
    }

}

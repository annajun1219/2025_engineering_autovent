package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.AlarmRequest;
import com.example._2025_engineering_autovent.dto.AlarmResponse;
import com.example._2025_engineering_autovent.entity.alarmSetting;
import com.example._2025_engineering_autovent.entity.users;
import com.example._2025_engineering_autovent.entity.windows;
import com.example._2025_engineering_autovent.entity.location;
import com.example._2025_engineering_autovent.repository.AlarmSettingRepository;
import com.example._2025_engineering_autovent.repository.UsersRepository;
import com.example._2025_engineering_autovent.repository.WindowsRepository;
import com.example._2025_engineering_autovent.repository.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @Autowired
    private AlarmSettingRepository alarmSettingRepository;

    @Autowired
    private WindowsRepository windowsRepository;

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UsersRepository usersRepository;

    // 1. 알람 목록 조회 (GET /alarm)
    @GetMapping
    public ResponseEntity<?> getAlarms(@RequestParam(required = false) String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "success", false,
                    "message", "이메일 파라미터가 필요합니다."
            ));
        }

        List<alarmSetting> alarms = alarmSettingRepository.findByWindowUserEmail(email);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        List<AlarmResponse> responseList = alarms.stream().map(alarm -> {
            String openTimeStr = alarm.getAlarmTime() != null ? sdf.format(alarm.getAlarmTime()) : null;
            String stateStr = (alarm.getIsActive() != null && alarm.getIsActive()) ? "on" : "off";

            return new AlarmResponse(
                    alarm.getAlarmId(),
                    alarm.getLocation(),
                    alarm.getWindow() != null ? alarm.getWindow().getIdWindows() : null,
                    alarm.getMode(),
                    openTimeStr,
                    alarm.getDurationMin() != null ? alarm.getDurationMin() : 0,
                    alarm.getRepeatType(),
                    stateStr
            );
        }).toList();

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "success", true,
                "message", "알람 목록 조회 성공",
                "data", responseList
        ));
    }

    // 알람 추가(설정) 메서드
    @PostMapping
    public ResponseEntity<?> addAlarm(
            @RequestParam String email,
            @RequestBody AlarmRequest req) {

        alarmSetting alarm = new alarmSetting();

        // 1. location 객체 세팅
        location locationEntity = locationRepository.findById(req.getLocation().getIdLocation())
                .orElseThrow(() -> new IllegalArgumentException("Invalid location"));

        // 2. windows 객체 세팅
        windows windowEntity = windowsRepository.findFirstByUserEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or no windows found"));

        // 3. *** users 객체 직접 조회 후 세팅 ***
        users userEntity = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));
        alarm.setUser(userEntity);

        // 4. 기존 코드 유지
        alarm.setLocation(locationEntity);
        alarm.setIdWindows(windowEntity.getIdWindows());  // getter/setter 네이밍 주의
        alarm.setMode(req.getMode());

        String openTimeStr = req.getOpen_time();
        if (!openTimeStr.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
            openTimeStr += ":00";
        }
        alarm.setAlarmTime(Time.valueOf(openTimeStr));

        alarm.setDurationMin(req.getPeriod());
        alarm.setRepeatType(req.getRepeat());
        alarm.setIsActive("on".equalsIgnoreCase(req.getState()));

        alarmSettingRepository.save(alarm);

        return ResponseEntity.ok(Map.of(
                "status", 200,
                "success", true,
                "message", "알람 설정 성공",
                "data", alarm
        ));
    }

    // 3. 알람 삭제 (DELETE /alarm/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlarm(@PathVariable Integer id) {
        if (alarmSettingRepository.existsById(id)) {
            alarmSettingRepository.deleteById(id);
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "success", true,
                    "message", "알람 삭제 성공"));
        }
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "success", false,
                "message", "알람 없음"));
    }
}

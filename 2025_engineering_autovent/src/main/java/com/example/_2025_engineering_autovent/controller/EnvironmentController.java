package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.EnvironmentSettingRequest;
import com.example._2025_engineering_autovent.dto.EnvironmentSettingResponse;
import com.example._2025_engineering_autovent.entity.location;
import com.example._2025_engineering_autovent.entity.windows;
import com.example._2025_engineering_autovent.repository.LocationRepository;
import com.example._2025_engineering_autovent.repository.WindowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/setting")
public class EnvironmentController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WindowsRepository windowsRepository;

    @PostMapping(produces = "application/json")
    public ResponseEntity<?> setEnvironment(@RequestBody EnvironmentSettingRequest req) {
        // 1) location/window 존재 확인
        Optional<location> locationOpt = locationRepository.findByIdLocation(req.getLocation_id());
        Optional<windows> windowOpt = windowsRepository.findByIdWindows(req.getWindow_id());

        if (locationOpt.isPresent() && windowOpt.isPresent()) {
            // 성공 응답
            EnvironmentSettingResponse.Data d = new EnvironmentSettingResponse.Data();
            d.setLocation_id(req.getLocation_id());
            d.setWindow_id(req.getWindow_id());
            d.setMode(req.getMode());
            d.setOpen_time(req.getOpen_time());
            d.setPeriod(req.getPeriod());

            EnvironmentSettingResponse res = new EnvironmentSettingResponse();
            res.setStatus(200);
            res.setSuccess(true);
            res.setMessage("환경 설정 성공");
            res.setData(Collections.singletonList(d));
            return ResponseEntity.ok(res);
        } else {
            // 실패(존재하지 않는 경우)
            EnvironmentSettingResponse err = new EnvironmentSettingResponse();
            err.setStatus(500);
            err.setSuccess(false);
            err.setMessage("서버 오류 또는 location/window 없음");
            err.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
        }
    }
}

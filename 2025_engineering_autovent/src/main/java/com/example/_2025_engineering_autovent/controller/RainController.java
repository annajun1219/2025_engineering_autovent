package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.DustRequest;
import com.example._2025_engineering_autovent.dto.DustResponse;
import com.example._2025_engineering_autovent.dto.RainRequest;
import com.example._2025_engineering_autovent.dto.RainResponse;
import com.example._2025_engineering_autovent.entity.location;
import com.example._2025_engineering_autovent.entity.windows;
import com.example._2025_engineering_autovent.repository.LocationRepository;
import com.example._2025_engineering_autovent.repository.UsersRepository;
import com.example._2025_engineering_autovent.repository.WindowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/rain")
public class RainController {

    @Autowired
    private WindowsRepository windowsRepository;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<RainResponse> handleDust(@RequestParam String email,
                                                   @RequestBody RainRequest req) {

        // 로그인한 유저의 윈도우 정보 가져오기
        Optional<windows> windowOpt = windowsRepository.findFirstByUserEmail(email);

        RainResponse response = new RainResponse();

        if (windowOpt.isPresent()) {
            windows window = windowOpt.get();

            RainResponse.Data data = new RainResponse.Data();
            data.setLocation_id(window.getLocation().getIdLocation());
            data.setWindow_id(window.getIdWindows());
            data.setMode("rain"); // 강제로 dust 모드
            data.setTime_stamp(req.getTime_stamp());

            response.setStatus(200);
            response.setSuccess(true);
            response.setMessage("우천 모드가 설정되었습니다.");
            response.setData(Collections.singletonList(data));

            return ResponseEntity.ok(response);

        } else {
            response.setStatus(404);
            response.setSuccess(false);
            response.setMessage("오류입니다.");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

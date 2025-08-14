package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.DustRequest;
import com.example._2025_engineering_autovent.dto.DustResponse;
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
@RequestMapping("/dust")
public class DustController {

    @Autowired
    private WindowsRepository windowsRepository;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<DustResponse> handleDust(@RequestParam String email,
                                                   @RequestBody DustRequest req) {

        // 로그인한 유저의 윈도우 정보 가져오기
        Optional<windows> windowOpt = windowsRepository.findFirstByUserEmail(email);

        DustResponse response = new DustResponse();

        if (windowOpt.isPresent()) {
            windows window = windowOpt.get();

            DustResponse.Data data = new DustResponse.Data();
            data.setLocation_id(window.getLocation().getIdLocation());
            data.setWindow_id(window.getIdWindows());
            data.setMode("dust"); // 강제로 dust 모드
            data.setTime_stamp(req.getTime_stamp());

            response.setStatus(200);
            response.setSuccess(true);
            response.setMessage("미세먼지 데이터 처리 성공");
            response.setData(Collections.singletonList(data));

            return ResponseEntity.ok(response);

        } else {
            response.setStatus(404);
            response.setSuccess(false);
            response.setMessage("해당 유저의 창문 정보를 찾을 수 없습니다.");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

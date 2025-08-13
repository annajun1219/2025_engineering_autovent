package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.LoginRequest;
import com.example._2025_engineering_autovent.dto.LoginResponse;
import com.example._2025_engineering_autovent.entity.users;
import com.example._2025_engineering_autovent.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<users> userOpt = usersRepository.findByEmailAndPasswords(
                loginRequest.getEmail(),
                loginRequest.getPasswords()
        );

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(new LoginResponse(200, true, "로그인 성공", loginRequest));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(400, false, "DB에 존재하지 않음", null));
        }
    }
}

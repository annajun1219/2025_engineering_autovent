package com.example._2025_engineering_autovent.dto;

// LoginRequest.java
public class LoginRequest {
    private String email;
    private String passwords;
    // Getter, Setter
    // 기본 생성자 (스프링이 JSON을 객체로 바인딩할 때 필요)
    public LoginRequest() {}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswords() {
        return passwords;
    }
    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }
}

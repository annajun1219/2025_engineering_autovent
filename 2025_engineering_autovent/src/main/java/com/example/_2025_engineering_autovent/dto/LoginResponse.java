package com.example._2025_engineering_autovent.dto;

// LoginResponse.java
public class LoginResponse {
    private int status;
    private boolean success;
    private String message;
    private Object data;

    public LoginResponse() {
    }

    // 전체 필드를 채우는 생성자
    public LoginResponse(int status, boolean success, String message, Object data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getter & Setter
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
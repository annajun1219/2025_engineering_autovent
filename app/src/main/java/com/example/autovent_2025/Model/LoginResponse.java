package com.example.autovent_2025.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private int status;
    private boolean success;
    private String message;
    private Data data;

    // 내부 클래스: data 부분 매핑
    public static class Data {
        @SerializedName("e-mail")
        private String email;

        @SerializedName("password")
        private String password;

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

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

    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
}

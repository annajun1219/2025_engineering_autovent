package com.example._2025_engineering_autovent.dto;

import com.example._2025_engineering_autovent.controller.DustController;

import java.util.List;

public class DustResponse {
    private int status;
    private boolean success;
    private String message;
    private List<DustResponse.Data> data; // 응답 데이터 목록

    public DustResponse() {}

    public DustResponse(int status, boolean success, String message, List<DustResponse.Data> data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }
    public int getStatus() {return status;}
    public void setStatus(int status) {}
    public boolean isSuccess() {return success;}
    public void setSuccess(boolean success) {this.success = success;}
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
    public List<DustResponse.Data> getData() {return data;}
    public void setData(List<DustResponse.Data> data) {this.data = data;}




    public static class Data {
        private int location_id;
        private int window_id;
        private String mode;
        private String time_stamp;

        public Data(){}

        public Data(int location_id, int window_id, String mode, String time_stamp) {
            this.location_id = location_id;
            this.window_id = window_id;
            this.mode = mode;
            this.time_stamp = time_stamp;
        }

        public int getLocation_id() {return  location_id;}
        public void setLocation_id(int location_id) {this.location_id = location_id;}
        public int getWindow_id() {return  window_id;}
        public void setWindow_id(int window_id) {this.window_id = window_id;}
        public String getMode() {return  mode;}
        public void setMode(String mode) {this.mode = mode;}
        public String getTime_stamp() {return  time_stamp;}
        public void setTime_stamp(String time_stamp) {this.time_stamp = time_stamp;}

    }
}

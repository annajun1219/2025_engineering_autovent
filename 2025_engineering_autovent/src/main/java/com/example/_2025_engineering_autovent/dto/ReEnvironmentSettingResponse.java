package com.example._2025_engineering_autovent.dto;

import java.util.List;

public class ReEnvironmentSettingResponse {
    private int status;
    private boolean success;
    private String message;
    private List<Data> data; // 응답 데이터 목록

    // 기본 생성자
    public ReEnvironmentSettingResponse() {}

    public ReEnvironmentSettingResponse(int status, boolean success, String message, List<Data> data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getter / Setter
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

    public List<Data> getData() {
        return data;
    }
    public void setData(List<Data> data) {
        this.data = data;
    }

    // 내부 Data 클래스
    public static class Data {
        private int location_id;
        private String window_id;
        private String mode;
        private String open_time;
        private int period;

        public Data() {}

        public Data(int location_id, String window_id, String mode, String open_time, int period) {
            this.location_id = location_id;
            this.window_id = window_id;
            this.mode = mode;
            this.open_time = open_time;
            this.period = period;
        }

        public int getLocation_id() {
            return location_id;
        }
        public void setLocation_id(int location_id) {
            this.location_id = location_id;
        }

        public String getWindow_id() {
            return window_id;
        }
        public void setWindow_id(String  window_id) {
            this.window_id = window_id;
        }

        public String getMode() {
            return mode;
        }
        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getOpen_time() {
            return open_time;
        }
        public void setOpen_time(String open_time) {
            this.open_time = open_time;
        }

        public int getPeriod() {
            return period;
        }
        public void setPeriod(int period) {
            this.period = period;
        }
    }
}


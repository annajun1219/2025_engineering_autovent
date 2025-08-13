package com.example._2025_engineering_autovent.dto;

// EnvironmentSettingRequest.java
public class EnvironmentSettingRequest {
    private int location_id;
    private int window_id;
    private String mode;
    private String open_time;
    private int period;

    //기본 생성자, getter, setter
    public EnvironmentSettingRequest(){}

    public int getLocation_id(){
        return location_id;
    }
    public void setLocation_id(int location_id){
        this.location_id = location_id;
    }
    public int getWindow_id(){
        return window_id;
    }

    public void setWindow_id(int window_id) {
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

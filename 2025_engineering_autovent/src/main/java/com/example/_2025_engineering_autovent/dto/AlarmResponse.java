package com.example._2025_engineering_autovent.dto;

import com.example._2025_engineering_autovent.entity.location;

public class AlarmResponse {

    private Integer alarmId;

    private location location;          // location 객체 전체 유지

    private String window_id;           // window_id만 String 필드로 처리

    private String mode;

    private String open_time;           // alarmTime을 문자열(HH:mm:ss)로 변환

    private int period;                 // durationMin

    private String repeat;              // repeatType

    private String state;               // isActive -> "on"/"off" 변환

    public AlarmResponse() {
    }

    public AlarmResponse(Integer alarmId, location location, String window_id,
                         String mode, String open_time, int period,
                         String repeat, String state) {
        this.alarmId = alarmId;
        this.location = location;
        this.window_id = window_id;
        this.mode = mode;
        this.open_time = open_time;
        this.period = period;
        this.repeat = repeat;
        this.state = state;
    }

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public location getLocation() {
        return location;
    }

    public void setLocation(location location) {
        this.location = location;
    }

    public String getWindow_id() {
        return window_id;
    }

    public void setWindow_id(String window_id) {
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

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

package com.example._2025_engineering_autovent.dto;

import com.example._2025_engineering_autovent.entity.location;

import java.sql.Time;

public class AlarmRequest {
    private location location;
    private String window_id;
    private String mode;
    private String open_time;   // 시:분, 예: "12:00"
    private int period;         // 오픈 지속 시간(분)
    private String repeat;      // "everyday"/"weekday" 등
    private String state;       // "on"/"off"

    public location getLocation() { return location; }
    public void setLocation(location location) { this.location = location; }

    public String getWindow_id() {return window_id;}
    public void setWindow_id(String window_id) {this.window_id = window_id;}
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

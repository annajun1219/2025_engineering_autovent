package com.example._2025_engineering_autovent.dto;

public class RainRequest {
    private int location_id;
    private String window_id;
    private String mode;
    private String time_stamp;

    public RainRequest() {}

    public int getLocation_id() {return location_id;}
    public void setLocation_id(int location_id) {this.location_id = location_id;}
    public String getWindow_id() {return window_id;}
    public void setWindow_id(String window_id) {this.window_id = window_id;}
    public String getMode() {return mode;}
    public void setMode(String mode) {this.mode = mode;}
    public String getTime_stamp() {return time_stamp;}
    public void setTime_stamp(String time_stamp) {this.time_stamp = time_stamp;}

}
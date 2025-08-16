package com.example._2025_engineering_autovent.dto;

public class ArduinoRainRequest {
    private int location_id;
    private String window_id;
    private int level;

    public int getLocation_id() {return location_id;}
    public void setLocation_id(int location_id) {this.location_id = location_id;}
    public String getWindowId() { return window_id; }
    public void setWindowId(String windowId) { this.window_id = windowId; }
    public int getLevel() {return level;}
    public void setLevel(int level) {this.level = level;}
}

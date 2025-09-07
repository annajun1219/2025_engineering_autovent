package com.example._2025_engineering_autovent.dto;

import com.example._2025_engineering_autovent.entity.location;
import com.example._2025_engineering_autovent.entity.location;

public class ArduinoBatteryRequest {

    private int sensor_id;
    private String sensor_type;
    private location location;  // Location 엔티티 포함
    private String window_id;
    private int level;

    public int getSensor_id() { return sensor_id; }
    public void setSensor_id(int sensor_id) { this.sensor_id = sensor_id; }

    public String getSensor_type() { return sensor_type; }
    public void setSensor_type(String sensor_type) { this.sensor_type = sensor_type; }

    public location getLocation() { return location; }
    public void setLocation(location location) { this.location = location; }

    public String getWindow_id() { return window_id; }
    public void setWindow_id(String window_id) { this.window_id = window_id; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}


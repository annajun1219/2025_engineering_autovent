package com.example._2025_engineering_autovent.dto;

import com.example._2025_engineering_autovent.entity.location;

public class ArduinoFireSensorRequest {
    private int sensor_id;
    private String sensor_type;
    private location location;
    private String window_id;
    private int fire;
    private String time_stamp;

    // 기본 생성자
    public ArduinoFireSensorRequest() {}

    // 전체 필드 생성자
    public ArduinoFireSensorRequest(int sensor_id, String sensor_type, location location,
                                    String window_id, int fire, String time_stamp) {
        this.sensor_id = sensor_id;
        this.sensor_type = sensor_type;
        this.location = location;
        this.window_id = window_id;
        this.fire = fire;
        this.time_stamp = time_stamp;
    }

    // getter/setter
    public int getSensor_id() { return sensor_id; }
    public void setSensor_id(int sensor_id) { this.sensor_id = sensor_id; }

    public String getSensor_type() { return sensor_type; }
    public void setSensor_type(String sensor_type) { this.sensor_type = sensor_type; }

    public location getLocation() { return location; }
    public void setLocation(location location) { this.location = location; }

    public String getWindow_id() { return window_id; }
    public void setWindow_id(String window_id) { this.window_id = window_id; }

    public int getFire() { return fire; }
    public void setFire(int fire) { this.fire = fire; }

    public String getTime_stamp() { return time_stamp; }
    public void setTime_stamp(String time_stamp) { this.time_stamp = time_stamp; }
}


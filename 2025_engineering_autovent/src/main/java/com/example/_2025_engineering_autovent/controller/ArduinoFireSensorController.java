package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.ArduinoFireSensorRequest;
import com.example._2025_engineering_autovent.entity.location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fire")
public class ArduinoFireSensorController {

    private volatile int latestSensorId = 0;
    private volatile String latestSensorType = "";
    private volatile location latestLocation;
    private volatile String latestWindowId = "";
    private volatile int latestFire = 0;
    private volatile String latestTimestamp = "";

    @PostMapping(value = "/arduino", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateFireSensor(@RequestBody ArduinoFireSensorRequest request) {
        latestSensorId = request.getSensor_id();
        latestSensorType = request.getSensor_type();
        latestLocation = request.getLocation();
        latestWindowId = request.getWindow_id();
        latestFire = request.getFire();
        latestTimestamp = request.getTime_stamp();

        System.out.println("sensor_id: " + latestSensorId
                + ", sensor_type: " + latestSensorType
                + ", location: " + latestLocation
                + ", window_id: " + latestWindowId
                + ", fire: " + latestFire
                + ", time_stamp: " + latestTimestamp);

        return ResponseEntity.ok().body(
                new ResponseMessage("success", "Fire sensor data updated",
                        latestSensorId, latestSensorType,
                        latestLocation, latestWindowId,
                        latestFire, latestTimestamp)
        );
    }

    @GetMapping(value = "/arduino", produces = "application/json")
    public ResponseEntity<?> getLatestFireSensor() {
        return ResponseEntity.ok().body(
                new FireSensorResponse(latestSensorId, latestSensorType,
                        latestLocation, latestWindowId,
                        latestFire, latestTimestamp)
        );
    }

    static class ResponseMessage {
        private String status;
        private String message;
        private int sensor_id;
        private String sensor_type;
        private location location;
        private String window_id;
        private int fire;
        private String time_stamp;

        public ResponseMessage(String status, String message,
                               int sensor_id, String sensor_type,
                               location location, String window_id, int fire,
                               String time_stamp) {
            this.status = status;
            this.message = message;
            this.sensor_id = sensor_id;
            this.sensor_type = sensor_type;
            this.location = location;
            this.window_id = window_id;
            this.fire = fire;
            this.time_stamp = time_stamp;
        }

        public String getStatus() { return status; }
        public String getMessage() { return message; }
        public int getSensor_id() { return sensor_id; }
        public String getSensor_type() { return sensor_type; }
        public location getLocation() { return location; }
        public String getWindow_id() { return window_id; }
        public int getFire() { return fire; }
        public String getTime_stamp() { return time_stamp; }
    }

    static class FireSensorResponse {
        private int sensor_id;
        private String sensor_type;
        private location location;
        private String window_id;
        private int fire;
        private String time_stamp;

        public FireSensorResponse(int sensor_id, String sensor_type,
                                  location location, String window_id,
                                  int fire, String time_stamp) {
            this.sensor_id = sensor_id;
            this.sensor_type = sensor_type;
            this.location = location;
            this.window_id = window_id;
            this.fire = fire;
            this.time_stamp = time_stamp;
        }

        public int getSensor_id() { return sensor_id; }
        public String getSensor_type() { return sensor_type; }
        public location getLocation() { return location; }
        public String getWindow_id() { return window_id; }
        public int getFire() { return fire; }
        public String getTime_stamp() { return time_stamp; }
    }
}

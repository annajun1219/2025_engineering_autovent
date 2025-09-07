package com.example._2025_engineering_autovent.controller;

import com.example._2025_engineering_autovent.dto.ArduinoRainSensorRequest;
import com.example._2025_engineering_autovent.entity.location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;

@RestController
@RequestMapping("/rain/sensor")
public class ArduinoRainSensorController {

    private volatile int latestSensorId = 0;
    private volatile String latestSensorType = "";
    private volatile location latestLocation;
    private volatile String latestWindowId = "";
    private volatile int latestRainLevel = 0;
    private volatile String latestTimestamp = "";

    @PostMapping(value = "/arduino", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateRainSensor(@RequestBody ArduinoRainSensorRequest request) {
        latestSensorId = request.getSensor_id();
        latestSensorType = request.getSensor_type();
        latestLocation = request.getLocation();
        latestWindowId = request.getWindow_id();
        latestRainLevel = request.getRainlevel();
        latestTimestamp = request.getTime_stamp();

        System.out.println("sensor_id: " + latestSensorId
                + ", sensor_type: " + latestSensorType
                + ", location: " + latestLocation
                + ", window_id: " + latestWindowId
                + ", rainLevel: " + latestRainLevel
                + ", time_stamp: " + latestTimestamp);

        return ResponseEntity.ok().body(
                new ResponseMessage("success", "Rain sensor data updated",
                        latestSensorId, latestSensorType,
                        latestLocation, latestWindowId,
                        latestRainLevel, latestTimestamp)
        );
    }

    @GetMapping(value = "/arduino", produces = "application/json")
    public ResponseEntity<?> getLatestRainSensor() {
        return ResponseEntity.ok().body(
                new RainSensorResponse(latestSensorId, latestSensorType,
                        latestLocation, latestWindowId,
                        latestRainLevel, latestTimestamp)
        );
    }

    static class ResponseMessage {
        private String status;
        private String message;
        private int sensor_id;
        private String sensor_type;
        private location location;
        private String window_id;
        private int rainlevel;
        private String time_stamp;

        public ResponseMessage(String status, String message,
                               int sensor_id, String sensor_type,
                               location location, String window_id, int rainlevel,
                               String time_stamp) {
            this.status = status;
            this.message = message;
            this.sensor_id = sensor_id;
            this.sensor_type = sensor_type;
            this.location = location;
            this.window_id = window_id;
            this.rainlevel = rainlevel;
            this.time_stamp = time_stamp;
        }

        // getters

        public String getStatus() { return status; }
        public String getMessage() { return message; }
        public int getSensor_id() { return sensor_id; }
        public String getSensor_type() { return sensor_type; }
        public location getLocation() { return location; }
        public String getWindow_id() { return window_id; }
        public int getRainlevel() { return rainlevel; }
        public String getTime_stamp() { return time_stamp; }
    }

    static class RainSensorResponse {
        private int sensor_id;
        private String sensor_type;
        private location location;
        private String window_id;
        private int rainlevel;
        private String time_stamp;

        public RainSensorResponse(int sensor_id, String sensor_type,
                                  location location,
                                  String window_id, int rainlevel,
                                  String time_stamp) {
            this.sensor_id = sensor_id;
            this.sensor_type = sensor_type;
            this.location = location;
            this.window_id = window_id;
            this.rainlevel = rainlevel;
            this.time_stamp = time_stamp;
        }

        // getters

        public int getSensor_id() { return sensor_id; }
        public String getSensor_type() { return sensor_type; }
        public location getLocation() { return location; }
        public String getWindow_id() { return window_id; }
        public int getRainlevel() { return rainlevel; }
        public String getTime_stamp() { return time_stamp; }
    }
}


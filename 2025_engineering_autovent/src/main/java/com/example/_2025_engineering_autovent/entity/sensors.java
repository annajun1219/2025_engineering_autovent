package com.example._2025_engineering_autovent.entity;

import com.example._2025_engineering_autovent.entity.location;
import com.example._2025_engineering_autovent.entity.windows;
import jakarta.persistence.*;

@Entity
@Table(name = "sensors")
public class sensors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private Integer sensorId;

    @Column(name = "sensor_type", nullable = false, length = 50)
    private String sensorType;

    // Location 엔티티와 ManyToOne 관계 (FK: location_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private location location;

    // Window 엔티티와 ManyToOne 관계 (FK: window_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "window_id")
    private windows window;

    // 기본 생성자 (필수)
    public sensors() {}

    // 생성자, getter/setter

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public location getLocation() {
        return location;
    }

    public void setLocation(location location) {
        this.location = location;
    }

    public windows getWindow() {
        return window;
    }

    public void setWindow(windows window) {
        this.window = window;
    }
}

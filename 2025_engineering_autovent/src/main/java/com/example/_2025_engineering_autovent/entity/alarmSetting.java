package com.example._2025_engineering_autovent.entity;

import jakarta.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "alarm_settings")
public class alarmSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Integer alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    private users user;  // users 테이블과 연관

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "window_id", referencedColumnName = "window_id", nullable = false)
    private windows window;  // windows 테이블과 연관

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false)
    private location location;  // location 테이블과 연관

    @Column(name = "mode", nullable = false, length = 50)
    private String mode;

    @Column(name = "alarm_time", nullable = false)
    private Time alarmTime;

    @Column(name = "duration_min", nullable = false)
    private Integer durationMin;

    @Column(name = "repeat_type", nullable = false, length = 50)
    private String repeatType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Getter / Setter

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public windows getWindow() {
        return window;
    }

    public void setWindow(windows window) {
        this.window = window;
    }

    public String getIdWindows() {
        return window != null ? window.getIdWindows() : null;
    }
    public void setIdWindows(String idWindows) {
        if (window == null) {
            window = new windows();
        }
        window.setIdWindows(idWindows);
    }

    public location getLocation() {
        return location;
    }

    public void setLocation(location location) {
        this.location = location;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Time getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Time alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Integer getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(Integer durationMin) {
        this.durationMin = durationMin;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

}

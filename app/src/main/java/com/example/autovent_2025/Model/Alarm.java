package com.example.autovent_2025.Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Alarm {
    public String id;
    public String label;
    public LocalTime time;
    public boolean enabled;
    public int openMinutes;
    public String repeatText;

    public String mode;
    public float cycleHours;
    public float timeHours;
    public LocalDateTime startTime;

    public Alarm(String id, String label, LocalTime time, boolean enabled,
                 int openMinutes, String repeatText,
                 String mode, float cycleHours, float timeHours, LocalDateTime startTime) {
        this.id = id;
        this.label = label;
        this.time = time;
        this.enabled = enabled;
        this.openMinutes = openMinutes;
        this.repeatText = repeatText;
        this.mode = mode;
        this.cycleHours = cycleHours;
        this.timeHours = timeHours;
        this.startTime = startTime;
    }

    // ===== AlarmActivity/Adapter에서 기대하는 게터들 =====
    public String getId() { return id; }
    public String getLabel() { return label; }
    public LocalTime getTime() { return time; }
    public boolean isEnabled() { return enabled; }
    public int getOpenMinutes() { return openMinutes; }
    // repeatText를 getRepeat()로 노출 (호환용)
    public String getRepeat() { return repeatText; }

    // (필요시) 기타 필드 게터
    public String getMode() { return mode; }
    public float getCycleHours() { return cycleHours; }
    public float getTimeHours() { return timeHours; }
    public LocalDateTime getStartTime() { return startTime; }

    // ===== 기존 포맷팅 유틸 =====
    public String timeText() {
        if (time == null) return "-- : --";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("a h : mm");
        return time.format(fmt).replace("AM", "오전").replace("PM", "오후");
    }

    public String subText() {
        int minutes = openMinutes > 0 ? openMinutes : Math.round(timeHours * 60f);
        String repeat = (repeatText == null || repeatText.isEmpty()) ? "-" : repeatText;
        return "창문 개방 지속 시간 : " + minutes + "분\n반복 : " + repeat;
    }
}

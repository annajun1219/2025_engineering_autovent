package com.example.autovent_2025.Model;

import com.google.gson.annotations.SerializedName;

public class EnvironmentSettingRequest {
    @SerializedName("location_id") private int locationId;
    @SerializedName("window_id")   private String windowId;
    @SerializedName("mode")        private String mode;      // "OPEN"/"CLOSE"/"AUTO"...
    @SerializedName("open_time")   private String openTime;  // "HH:mm" or null
    @SerializedName("period")      private int period;

    public int getLocationId() { return locationId; }
    public void setLocationId(int v) { locationId = v; }
    public String getWindowId() { return windowId; }
    public void setWindowId(String v) { windowId = v; }
    public String getMode() { return mode; }
    public void setMode(String v) { mode = v; }
    public String getOpenTime() { return openTime; }
    public void setOpenTime(String v) { openTime = v; }
    public int getPeriod() { return period; }
    public void setPeriod(int v) { period = v; }
}

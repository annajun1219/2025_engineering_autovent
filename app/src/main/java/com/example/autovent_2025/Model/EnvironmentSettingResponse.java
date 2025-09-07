package com.example.autovent_2025.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EnvironmentSettingResponse {
    @SerializedName("status")  private int status;
    @SerializedName("success") private boolean success;
    @SerializedName("message") private String message;
    @SerializedName("data")    private List<Data> data;

    public int getStatus() { return status; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Data> getData() { return data; }

    public static class Data {
        @SerializedName("location_id") private int locationId;
        @SerializedName("window_id")   private String windowId;
        @SerializedName("mode")        private String mode;
        @SerializedName("open_time")   private String openTime;
        @SerializedName("period")      private int period;

        public int getLocationId() { return locationId; }
        public String getWindowId() { return windowId; }
        public String getMode() { return mode; }
        public String getOpenTime() { return openTime; }
        public int getPeriod() { return period; }
    }
}

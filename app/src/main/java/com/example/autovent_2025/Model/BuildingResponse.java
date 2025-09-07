package com.example.autovent_2025.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BuildingResponse {
    @SerializedName("status")  private int status;
    @SerializedName("success") private boolean success;
    @SerializedName("message") private String message;
    @SerializedName("data")    private List<Data> data;

    public int getStatus() { return status; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Data> getData() { return data; }

    public static class Data {
        // BuildingController가 내려주는 키들
        @SerializedName("location")  private String location;   // 건물명
        @SerializedName("floor")     private String floor;      // 층
        @SerializedName("room_num")  private String roomNum;    // 호수
        @SerializedName("window_id") private String windowId;   // 창문 ID
        @SerializedName("on_off")    private String onOff;      // "on"/"off"

        public String getLocation() { return location; }
        public String getFloor() { return floor; }
        public String getRoomNum() { return roomNum; }
        public String getWindowId() { return windowId; }
        public String getOnOff() { return onOff; }
    }
}

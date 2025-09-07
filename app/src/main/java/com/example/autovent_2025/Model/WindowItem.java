package com.example.autovent_2025.Model;

public class WindowItem {
    // UI 표시용
    private String building;
    private String title;
    private boolean open;

    // 서버 호출용 (UI에는 안씀)
    private String windowId;  // /setting/building 응답에 있음
    private int locationId;   // 응답에 없으면 0으로 두고 필요 시 채움

    public WindowItem(String building, String title, boolean open) {
        this.building = building;
        this.title = title;
        this.open = open;
    }

    // --- UI getters/setters ---
    public String getBuilding() { return building; }
    public String getTitle() { return title; }
    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }

    // --- 서버 호출용 식별자 ---
    public String getWindowId() { return windowId; }
    public void setWindowId(String windowId) { this.windowId = windowId; }
    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
}

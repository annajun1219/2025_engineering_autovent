package com.example.autovent_2025.Model;

public class WindowItem {
    private String building;
    private String title;
    private boolean open;

    public WindowItem(String building, String title, boolean open) {
        this.building = building;
        this.title = title;
        this.open = open;
    }
    public String getBuilding() { return building; }
    public String getTitle() { return title; }
    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }
}

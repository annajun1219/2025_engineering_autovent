package com.example._2025_engineering_autovent.dto;

import java.util.List;

public class DustResponse {
    private int status;
    private boolean success;
    private String message;
    private List<DustResponse.Data> data; // 응답 데이터 목록

    public class Data {
    }
}

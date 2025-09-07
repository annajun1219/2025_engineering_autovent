// RetrofitService.java
package com.example.autovent_2025.Network;

import com.example.autovent_2025.Model.BuildingResponse;
import com.example.autovent_2025.Model.EnvironmentSettingRequest;
import com.example.autovent_2025.Model.EnvironmentSettingResponse;
import com.example.autovent_2025.Model.LoginRequest;
import com.example.autovent_2025.Model.LoginResponse;
import com.example.autovent_2025.Model.WindowItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {
    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // 창문/건물 목록 조회
    @GET("/setting/building")
    Call<BuildingResponse> getUserWindows(@Query("email") String email);

    // 환경/모드 설정 저장 (실제 경로로 교체)
    @POST("/setting/environment")
    Call<EnvironmentSettingResponse> saveEnvironmentSetting(@Body EnvironmentSettingRequest body);
}


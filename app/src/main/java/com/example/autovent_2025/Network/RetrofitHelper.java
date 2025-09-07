// RetrofitHelper.java
package com.example.autovent_2025.Network;

import com.example.autovent_2025.Model.BuildingResponse;
import com.example.autovent_2025.Model.EnvironmentSettingRequest;
import com.example.autovent_2025.Model.EnvironmentSettingResponse;
import com.example.autovent_2025.Model.LoginRequest;
import com.example.autovent_2025.Model.LoginResponse;
import com.example.autovent_2025.Model.WindowItem;

import retrofit2.Callback;

public class RetrofitHelper {

    private static RetrofitService service =
            RetrofitClient.getClient().create(RetrofitService.class);

    // 로그인 호출
    public static void login(String email, String passwords, Callback<LoginResponse> callback) {
        LoginRequest req = new LoginRequest(email, passwords);
        service.login(req).enqueue(callback);
    }

    // 창문/건물 목록 조회 (BuildingController)
    public static void fetchUserWindows(String email, Callback<BuildingResponse> callback) {
        service.getUserWindows(email).enqueue(callback);
    }

    // 환경/모드 설정 저장 (EnvironmentSetting*)
    public static void saveEnvironmentSetting(EnvironmentSettingRequest body,
                                              Callback<EnvironmentSettingResponse> callback) {
        service.saveEnvironmentSetting(body).enqueue(callback);
    }

    // 편의 오버로드
    public static void saveEnvironmentSetting(
            int locationId, String windowId, String mode, String openTime, int period,
            Callback<EnvironmentSettingResponse> callback
    ) {
        EnvironmentSettingRequest req = new EnvironmentSettingRequest();
        req.setLocationId(locationId);
        req.setWindowId(windowId);
        req.setMode(mode);
        req.setOpenTime(openTime);
        req.setPeriod(period);
        service.saveEnvironmentSetting(req).enqueue(callback);
    }
}

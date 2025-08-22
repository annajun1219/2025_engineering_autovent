// RetrofitHelper.java
package com.example.autovent_2025.Network;

import com.example.autovent_2025.Model.LoginRequest;
import com.example.autovent_2025.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class RetrofitHelper {

    private static RetrofitService service =
            RetrofitClient.getClient().create(RetrofitService.class);

    // 로그인 호출
    public static void login(String email, String passwords, Callback<LoginResponse> callback) {
        LoginRequest req = new LoginRequest(email, passwords);
        service.login(req).enqueue(callback);
    }
}

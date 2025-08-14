// RetrofitService.java
package com.example.autovent_2025.Network;

import com.example.autovent_2025.Model.LoginRequest;
import com.example.autovent_2025.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}

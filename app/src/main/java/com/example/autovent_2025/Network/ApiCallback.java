package com.example.autovent_2025.Network;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}

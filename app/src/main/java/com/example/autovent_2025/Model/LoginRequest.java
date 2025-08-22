package com.example.autovent_2025.Model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("passwords")
    private String passwords;

    public LoginRequest() {}

    public LoginRequest(String email, String passwords) {
        this.email = email;
        this.passwords = passwords;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswords() {
        return passwords;
    }
    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }
}

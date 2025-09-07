package com.example._2025_engineering_autovent.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class users {
    @Id
    private String email;
    private String passwords;

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    // 기본 생성자, Getter, Setter
}
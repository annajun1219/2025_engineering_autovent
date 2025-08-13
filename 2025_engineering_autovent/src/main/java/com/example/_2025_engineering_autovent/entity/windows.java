package com.example._2025_engineering_autovent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class windows {
    @Id
    @Column(name = "id_Windows")
    private int idWindows;
    // getters/setters
}

package com.example._2025_engineering_autovent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class location {
    @Id
    @Column(name = "id_Location")
    private int idLocation;
    // getters/setters
}
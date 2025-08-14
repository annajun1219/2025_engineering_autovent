package com.example._2025_engineering_autovent.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Location")
    private int idLocation;

    // getter/setter
    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }
}
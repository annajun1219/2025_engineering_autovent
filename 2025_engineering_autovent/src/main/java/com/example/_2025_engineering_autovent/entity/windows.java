package com.example._2025_engineering_autovent.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "windows")
public class windows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Windows")
    private int idWindows;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private users user; // FK - users.email

    @ManyToOne
    @JoinColumn(name = "id_Location", referencedColumnName = "id_Location")
    private location location; // FK - location.id_Location

    public windows() {} // 기본 생성자

    // ===== Getter / Setter =====
    public int getIdWindows() {
        return idWindows;
    }

    public void setIdWindows(int idWindows) {
        this.idWindows = idWindows;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public location getLocation() {
        return location;
    }

    public void setLocation(location location) {
        this.location = location;
    }
}

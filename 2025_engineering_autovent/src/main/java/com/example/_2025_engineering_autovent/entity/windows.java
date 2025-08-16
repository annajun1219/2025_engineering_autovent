package com.example._2025_engineering_autovent.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "windows")
public class windows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "window_id")
    private String idWindows;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private users user; // users 엔티티

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private location location; // location 엔티티

    // 기본 생성자
    public windows() {}

    // Getter/Setter
    public String getIdWindows() {
        return idWindows;
    }

    public void setIdWindows(String idWindows) {
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

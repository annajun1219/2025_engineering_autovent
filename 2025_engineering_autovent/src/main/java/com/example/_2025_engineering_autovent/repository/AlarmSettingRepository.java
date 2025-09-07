package com.example._2025_engineering_autovent.repository;

import com.example._2025_engineering_autovent.entity.alarmSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmSettingRepository extends JpaRepository<alarmSetting, Integer> {
    List<alarmSetting> findByWindowUserEmail(String email);
}


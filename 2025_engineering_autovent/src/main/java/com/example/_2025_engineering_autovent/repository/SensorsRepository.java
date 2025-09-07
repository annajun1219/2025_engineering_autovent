package com.example._2025_engineering_autovent.repository;

import com.example._2025_engineering_autovent.entity.location;
import com.example._2025_engineering_autovent.entity.sensors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<sensors, Integer> {
    Optional<sensors> findBySensorIdAndSensorType(int sensorId, String sensorType);
}

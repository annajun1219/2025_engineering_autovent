package com.example._2025_engineering_autovent.repository;

import com.example._2025_engineering_autovent.entity.location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<location, Integer> {

    Optional<location> findByIdLocation(int idLocation);
    Optional<location> findByLocationAndFloorAndRoomNum(String location, String floor, String roomNum);
}

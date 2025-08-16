package com.example._2025_engineering_autovent.repository;

import com.example._2025_engineering_autovent.entity.windows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WindowsRepository extends JpaRepository<windows, String> {
    // 또는 location_id까지 조건으로
    Optional<windows> findByIdWindows(String idWindows);
    Optional<windows> findFirstByUserEmail(String email);
    Optional<windows> findByUserEmailAndLocationIdLocation(String email, int idLocation);
}

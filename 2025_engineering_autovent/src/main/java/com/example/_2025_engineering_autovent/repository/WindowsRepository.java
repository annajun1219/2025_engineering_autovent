package com.example._2025_engineering_autovent.repository;

import com.example._2025_engineering_autovent.entity.location;
import com.example._2025_engineering_autovent.entity.windows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WindowsRepository extends JpaRepository<windows, Integer> {
    Optional<windows> findByIdWindows(int idWindows);
}

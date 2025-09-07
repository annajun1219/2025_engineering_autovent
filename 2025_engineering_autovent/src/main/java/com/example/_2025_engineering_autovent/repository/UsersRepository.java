package com.example._2025_engineering_autovent.repository;

import com.example._2025_engineering_autovent.entity.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<users, String> {
    Optional<users> findByEmailAndPasswords(String email, String passwords);
    Optional<users> findByEmail(String email);
}

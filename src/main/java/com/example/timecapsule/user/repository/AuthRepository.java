package com.example.timecapsule.user.repository;

import com.example.timecapsule.user.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
}

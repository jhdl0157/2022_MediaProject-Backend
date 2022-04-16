package com.example.timecapsule.user.repository;

import com.example.timecapsule.user.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findAuthByAccessToken(String accessToken);
}

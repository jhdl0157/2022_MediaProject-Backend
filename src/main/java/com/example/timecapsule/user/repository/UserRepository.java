package com.example.timecapsule.user.repository;

import com.example.timecapsule.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByUserNickname(String userNickname);
    Optional<User> findUserByUserEmail(String email);
}

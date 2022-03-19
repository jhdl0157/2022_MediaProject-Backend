package com.example.timecapsule.capsule.repository;

import com.example.timecapsule.capsule.entity.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapsuleRepository extends JpaRepository<Capsule,Long> {
}

package com.example.timecapsule.capsule.repository;

import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.capsule.entity.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CapsuleRepository extends JpaRepository<Capsule,Long> {
    Optional<Capsule> findCapsuleByCapsuleId(Long capsule_id);
    List<Capsule> findCapsulesByRecipient(Long recipient);
}

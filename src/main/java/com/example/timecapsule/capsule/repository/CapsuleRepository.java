package com.example.timecapsule.capsule.repository;

import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.capsule.dto.response.OpenCapsuleResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule,Long> {
    Optional<Capsule> findCapsuleByCapsuleId(Long capsule_id);

    List<Capsule> findCapsulesByRecipientOrderByCreatedAtDesc(String recipient);
    List<Capsule> findCapsulesBySenderId(String senderid);
}

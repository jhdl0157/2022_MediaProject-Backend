package com.example.timecapsule.account.repository;

import com.example.timecapsule.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findAccountByAccountEmail(String accountEmail);
    Optional<Account> findAccountByAccessToken(String accessToken);
}

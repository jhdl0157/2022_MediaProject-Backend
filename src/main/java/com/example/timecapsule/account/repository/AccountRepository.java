package com.example.timecapsule.account.repository;

import com.example.timecapsule.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

}

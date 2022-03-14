package com.example.timecapsule.account.service;


import com.example.timecapsule.account.dto.response.MyAccountResponse;
import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    public Account findAccountByAccountEmail(String accountEmail) {
        return accountRepository.findAccountByAccountEmail(accountEmail).orElse(null);
    }

    public MyAccountResponse insertAccount(Account newAccount) {
        Account saveAccount = accountRepository.save(newAccount);
        return MyAccountResponse.toAccountResponse(saveAccount);
    }
}

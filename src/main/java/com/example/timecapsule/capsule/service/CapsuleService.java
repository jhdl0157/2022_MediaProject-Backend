package com.example.timecapsule.capsule.service;

import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.account.service.AccountService;
import com.example.timecapsule.capsule.dto.CapsuleRequest;
import com.example.timecapsule.capsule.dto.response.ApiResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.repository.CapsuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CapsuleService {
    private final CapsuleRepository capsuleRepository;
    private final AccountService accountService;
    public Capsule createCapsule(String accessToken,CapsuleRequest capsuleRequest){
        Account account = accountService.findAccountByAccessToken(accessToken);
        Capsule capsule=capsuleRequest.toCapsule(account);
        capsuleRepository.save(capsule);
        return capsule;
    }
    public List<String> getRandomNickname(){
        RestTemplate restTemplate = new RestTemplate();
        java.lang.String fooResourceUrl= new java.lang.String("https://bloodgang.shop/api/nickname?count=4");
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(fooResourceUrl, ApiResponse.class);
        log.info(responseEntity.getBody().getWord().get(0));
        return responseEntity.getBody().getWord();
    }
}

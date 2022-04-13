package com.example.timecapsule.capsule.service;

import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.account.service.AccountService;
import com.example.timecapsule.capsule.dto.request.CapsuleRequest;
import com.example.timecapsule.capsule.dto.response.ApiResponse;
import com.example.timecapsule.capsule.dto.response.CapsuleResponse;
import com.example.timecapsule.capsule.dto.response.OpenCapsuleResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.repository.CapsuleRepository;
import com.example.timecapsule.exception.NotFoundException;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.repository.UserRepository;
import com.example.timecapsule.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CapsuleService {
    private final CapsuleRepository capsuleRepository;
    private final AccountService accountService;
    private final UserRepository userRepository;
    private final UserService userService;
    //캡슐 등록
    public CapsuleResponse createCapsule(final String accessToken, final CapsuleRequest capsuleRequest){
        User user=userService.findUserByAccessToken(accessToken);
        Capsule capsule = Capsule.of(capsuleRequest, user);
        capsuleRepository.save(capsule);
        return CapsuleResponse.toCapsuleResponse(capsule);
    }
    
    public List<String> getRandomNickname(){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl= "https://bloodgang.shop/api/v1/character";
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(fooResourceUrl, ApiResponse.class);
        log.info(responseEntity.getBody().getWord().get(0));
        return responseEntity.getBody().getWord();
    }

    public CapsuleResponse getDetailCapsule(final Long capsule_id) {
        Capsule capsule=capsuleRepository.findCapsuleByCapsuleId(capsule_id).orElseThrow(NotFoundException::new);
        if(!capsule.getIsOpened())
            capsule.setIsOpened(true);
        capsuleRepository.save(capsule);
        return CapsuleResponse.toCapsuleResponse(capsule);
    }

    public List<CapsuleResponse> getListCapsule(final String accessToken) {
        User user=userService.findUserByAccessToken(accessToken);
        List<CapsuleResponse> capsuleResponseList=new ArrayList<>();
        List<Capsule> listcapsule=capsuleRepository.findCapsulesByRecipientOrderByCreatedAtDesc(user.getUserId());
        for (Capsule capsule : listcapsule) {
            capsuleResponseList.add(CapsuleResponse.toCapsuleResponse(capsule));
        }
        return capsuleResponseList;
    }

    public void deleteCapsule(final Long capsuleId,final String accessToken) {
        Capsule nowCapsule=capsuleRepository.findById(capsuleId).orElseThrow(NotFoundException::new);
        User nowuser=userService.findUserByAccessToken(accessToken);
        if(!nowCapsule.getRecipient().equals(nowuser.getUserId())) {
            throw new IllegalArgumentException();
        }
        capsuleRepository.deleteById(capsuleId);
    }

    public List<OpenCapsuleResponse> OpenedCapsule(final String accessToken) {
        User user=userService.findUserByAccessToken(accessToken);
        List<OpenCapsuleResponse> capsuleResponseList=new ArrayList<>();
        //수정
        List<Capsule> capsuleList=capsuleRepository.findCapsulesBySenderId(user.getUserId());
        for(Capsule capsule : capsuleList){
            capsuleResponseList.add(OpenCapsuleResponse.toOpenCapsule(capsule));
        }
        return capsuleResponseList;
    }
}

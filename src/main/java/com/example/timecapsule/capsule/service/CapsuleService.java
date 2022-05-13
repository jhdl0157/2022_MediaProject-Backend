package com.example.timecapsule.capsule.service;

import com.example.timecapsule.capsule.dto.request.CapsuleRequest;
import com.example.timecapsule.capsule.dto.response.ApiResponse;
import com.example.timecapsule.capsule.dto.response.CapsuleResponse;
import com.example.timecapsule.capsule.dto.response.OpenCapsuleResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.repository.CapsuleRepository;
import com.example.timecapsule.exception.NOTFOUNDEXCEPTION;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CapsuleService {
    private final CapsuleRepository capsuleRepository;
    private final UserService userService;
    private static final String RANDOM_NICKNAME_API_URL = "https://bloodgang.shop/api/v1/character";

    //캡슐 등록
    public CapsuleResponse createCapsule(final String accessToken, final CapsuleRequest capsuleRequest) {
        User user = userService.findUserByAccessToken(accessToken);

        Capsule capsule = Capsule.builder()
                .user(user)
                .capsuleContent(capsuleRequest.getContent())
                .duration(capsuleRequest.getDuration())
                .isOpened(false)
                .recipient(capsuleRequest.getRecipient())
                .nickname(capsuleRequest.getNickname())
                .senderId(user.getUserId())
                .location(capsuleRequest.setLocationFunc(capsuleRequest.getLatitude(), capsuleRequest.getLongitude()))
                .build();
        capsuleRepository.save(capsule);
        return CapsuleResponse.toCapsuleResponse(capsule);
    }

    public String getRandomNickname() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = RANDOM_NICKNAME_API_URL;
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(fooResourceUrl, ApiResponse.class);
        log.info(responseEntity.getBody().getWord().get(0));
        return responseEntity.getBody().getWord().get(0);
    }

    public CapsuleResponse getDetailCapsule(final Long capsule_id) {
        Capsule capsule = capsuleRepository.findCapsuleByCapsuleId(capsule_id).orElseThrow(NOTFOUNDEXCEPTION::new);
        if (!capsule.getIsOpened())
            capsule.setIsOpened(true);
        capsuleRepository.save(capsule);
        return CapsuleResponse.toCapsuleResponse(capsule);
    }

    public List<CapsuleResponse> getListCapsule(final String accessToken) {
        User user = userService.findUserByAccessToken(accessToken);
        return capsuleRepository.findCapsulesByRecipientOrderByCreatedAtDesc(user.getUserId()).stream()
                .map(CapsuleResponse::toCapsuleResponse)
                .collect(Collectors.toList());
    }

    public int deleteCapsule(final Long capsuleId, final String accessToken) {
        Capsule nowCapsule = capsuleRepository.findById(capsuleId).orElseThrow(NOTFOUNDEXCEPTION::new);
        User nowuser = userService.findUserByAccessToken(accessToken);
        if (nowCapsule.getRecipient().equals(nowuser.getUserId())) {
            capsuleRepository.deleteById(capsuleId);
            //TODO 분기 다시 생각하기
            return 200;
        }
        return 401;

    }

    public List<OpenCapsuleResponse> OpenedCapsule(final String accessToken) {
        User user = userService.findUserByAccessToken(accessToken);
        return capsuleRepository.findCapsulesBySenderId(user.getUserId()).stream()
                .map(OpenCapsuleResponse::toOpenCapsule)
                .collect(Collectors.toList());
    }
}

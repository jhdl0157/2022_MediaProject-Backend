package com.example.timecapsule.capsule.service;

import com.example.timecapsule.capsule.dto.request.AnywhereCapsuleRequest;
import com.example.timecapsule.capsule.dto.request.LocationRequest;
import com.example.timecapsule.capsule.dto.request.SpecialCapsuleRequest;
import com.example.timecapsule.capsule.dto.response.ApiResponse;
import com.example.timecapsule.capsule.dto.response.SpecialCapsuleResponse;
import com.example.timecapsule.capsule.dto.response.OpenCapsuleResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.repository.CapsuleRepository;
import com.example.timecapsule.exception.NOTFOUNDEXCEPTION;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.repository.UserRepository;
import com.example.timecapsule.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CapsuleService {
    private final CapsuleRepository capsuleRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private static final String RANDOM_NICKNAME_API_URL = "http://bloodgang.shop:8080/api/v1/character";

    //캡슐 등록
    public SpecialCapsuleResponse createCapsule(final String accessToken, final SpecialCapsuleRequest capsuleRequest) {
        User user = userService.findUserByAccessToken(accessToken);
        User recipient=userRepository.findById(capsuleRequest.getRecipient()).orElseThrow(NOTFOUNDEXCEPTION::new);
        Capsule capsule = Capsule.builder()
                .user(user)
                .capsuleContent(capsuleRequest.getContent())
                .duration(capsuleRequest.getDuration())
                .isOpened(false)
                .recipient(recipient.getUserNickname())
                .recipientId(recipient.getId())
                .nickname(capsuleRequest.getNickname())
                .capsuleType(capsuleRequest.getCapsuleType())
                .senderId(user.getUserId())
                .location(capsuleRequest.setLocationFunc(capsuleRequest.getLatitude(), capsuleRequest.getLongitude()))
                .build();
        capsuleRepository.save(capsule);
        return SpecialCapsuleResponse.toCapsuleResponse(capsule);
    }

    public SpecialCapsuleResponse createCapsule(final String accessToken, final AnywhereCapsuleRequest capsuleRequest) {
        User user = userService.findUserByAccessToken(accessToken);
        User recipient=userRepository.findById(capsuleRequest.getRecipient()).orElseThrow(NOTFOUNDEXCEPTION::new);
        Capsule capsule = Capsule.builder()
                .user(user)
                .capsuleContent(capsuleRequest.getContent())
                .duration(capsuleRequest.getDuration())
                .isOpened(false)
                .recipient(recipient.getUserNickname())
                .recipientId(recipient.getId())
                .nickname(capsuleRequest.getNickname())
                .capsuleType(capsuleRequest.getCapsuleType())
                .senderId(user.getUserId())
                .build();
        capsuleRepository.save(capsule);
        return SpecialCapsuleResponse.toCapsuleResponse(capsule);
    }

    public String getRandomNickname() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = RANDOM_NICKNAME_API_URL;
        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(fooResourceUrl, ApiResponse.class);
            log.info(responseEntity.getBody().getWord().get(0));
            return responseEntity.getBody().getWord().get(0);
        }catch (HttpClientErrorException e){
            throw new NOTFOUNDEXCEPTION();
        }catch (RestClientException e){
            e.printStackTrace();
            throw new NOTFOUNDEXCEPTION();
        }
    }

    public SpecialCapsuleResponse getDetailCapsule(final Long capsule_id) {
        Capsule capsule = capsuleRepository.findCapsuleByCapsuleId(capsule_id).orElseThrow(NOTFOUNDEXCEPTION::new);
        if (!capsule.getIsOpened())
            capsule.setIsOpened(true);
        capsuleRepository.save(capsule);
        return SpecialCapsuleResponse.toCapsuleResponse(capsule);
    }


    public List<SpecialCapsuleResponse> getListCapsule(final String accessToken) {
        User user = userService.findUserByAccessToken(accessToken);
        return capsuleRepository.findCapsulesByRecipientIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(SpecialCapsuleResponse::toCapsuleResponse)
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

    public List<SpecialCapsuleResponse> OpenedCapsule(final String accessToken) {
        User user = userService.findUserByAccessToken(accessToken);
        return capsuleRepository.findCapsulesBySenderId(user.getUserId()).stream()
                .map(SpecialCapsuleResponse::toCapsuleResponse)
                .collect(Collectors.toList());
    }

    public Boolean check(LocationRequest locationRequest) {
        Capsule capsule=capsuleRepository.findById(locationRequest.getCapsuleId()).orElseThrow(NOTFOUNDEXCEPTION::new);
        double distance=distance(locationRequest.getLatitude(),locationRequest.getLongitude(),capsule.getLocation().getX(),capsule.getLocation().getY());
        if(distance<=300.0) return true;
        return false;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        return (dist * 60 * 1.1515*1609.344);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}

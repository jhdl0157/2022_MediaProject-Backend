package com.example.timecapsule.capsule.controller;

import com.example.timecapsule.capsule.dto.CapsuleRequest;
import com.example.timecapsule.capsule.dto.response.ApiResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.capsule.service.CapsuleService;
import com.example.timecapsule.main.common.ListResult;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/capsule")
public class CapsuleController {
    private final CapsuleService capsuleService;
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<SingleResult<Capsule>> postCapsule(
            @RequestHeader("Authorization") String accessToken,
            @RequestPart(value = "capsule") CapsuleRequest capsuleRequest
    ){
        String[] splitToken = accessToken.split(" ");
        Capsule capsule=capsuleService.createCapsule(splitToken[1],capsuleRequest);
        return new ResponseEntity<>(responseService.getSingleResult(capsule), HttpStatus.CREATED);
    }
    @GetMapping("/nickname")
    public ResponseEntity<ListResult<String>> getNicknmae() {
    return new ResponseEntity<>(responseService.getListResult(capsuleService.getRandomNickname()),HttpStatus.OK);
    }
}

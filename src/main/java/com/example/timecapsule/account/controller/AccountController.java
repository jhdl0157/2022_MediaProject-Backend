package com.example.timecapsule.account.controller;


import com.example.timecapsule.account.service.AuthService;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final ResponseService responseService;
    private final AuthService authService;
    @GetMapping("/login-url")
    public ResponseEntity<SingleResult<String>> loginUrl() {
        return new ResponseEntity<>(responseService.getSingleResult(authService.getKakaoLoginUrl()), HttpStatus.OK);
    }

}

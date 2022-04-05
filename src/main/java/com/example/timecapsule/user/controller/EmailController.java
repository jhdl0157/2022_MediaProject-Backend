package com.example.timecapsule.user.controller;

import com.example.timecapsule.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/email") // 이메일 인증 코드 보내기
    public ResponseEntity<String> emailAuth(@RequestParam String email) throws Exception {
        emailService.sendSimpleMessage(email);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/email/verifyCode") // 이메일 인증 코드 검증
    public ResponseEntity<String> verifyCode(@RequestParam String code) {
        if(EmailService.emailAuthCode.equals(code)) {
            return ResponseEntity.status(HttpStatus.OK).body("success");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("fail");
        }
    }
}

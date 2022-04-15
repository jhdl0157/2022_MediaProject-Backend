package com.example.timecapsule.user.controller;

import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
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
    private final ResponseService responseService;

    @PostMapping("/email") // 이메일 인증 코드 보내기
    public ResponseEntity<SingleResult<String>> emailAuth(@RequestParam String email) throws Exception {
        emailService.sendSimpleMessage(email);
        //return ResponseEntity.status(HttpStatus.OK).body("success");
        return new ResponseEntity<>(responseService.getSingleResult("success"), HttpStatus.OK);
    }

    @PostMapping("/email/verifyCode") // 이메일 인증 코드 검증
    public ResponseEntity<SingleResult<String>> verifyCode(@RequestParam String code) {
        if(EmailService.emailAuthCode.equals(code)) {
            return new ResponseEntity<>(responseService.getSingleResult("success"), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(responseService.getSingleResult("fail"), HttpStatus.BAD_REQUEST);
        }
    }
}

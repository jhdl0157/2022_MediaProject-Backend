package com.example.timecapsule.user.controller;

import com.example.timecapsule.account.dto.response.MyAccountResponse;
import com.example.timecapsule.main.common.CommonResult;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import com.example.timecapsule.user.dto.TokenResponseDto;
import com.example.timecapsule.user.dto.UserRequestDto;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.jwt.JwtTokenProvider;
import com.example.timecapsule.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/user")
    public ResponseEntity<SingleResult<String>> saveUser(@RequestBody UserRequestDto userRequestDto){
        userService.register(userRequestDto);
        return new ResponseEntity<>(responseService.getSingleResult("created user"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<SingleResult<TokenResponseDto>> login(@RequestBody UserRequestDto userRequestDto) throws Exception{
        //return ResponseEntity.ok(userService.login(userRequestDto));
        TokenResponseDto res = userService.login(userRequestDto);
        return new ResponseEntity<>(responseService.getSingleResult(res), HttpStatus.OK);
    }

    @GetMapping("/user/userIdCheck")
    public ResponseEntity<SingleResult<String>> userIdDuplicationCheck(@RequestParam String userId){
        if (userService.isUserIdDuplicated(userId)){
            return new ResponseEntity<>(responseService.getSingleResult("user id already exists"), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(responseService.getSingleResult("available user id"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/userNicknameCheck")
    public ResponseEntity<SingleResult<String>> userNicknameDuplicationCheck(@RequestParam String userNickname){
        if (userService.isUserNicknameDuplicated(userNickname)){
            return new ResponseEntity<>(responseService.getSingleResult("user nickname already exists"), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(responseService.getSingleResult("available user nickname"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public String test(@RequestHeader Map<String, String> headers){
        System.out.println("headers = " + headers);
        String token = headers.get("x-auth-token");
        System.out.println("token = " + token);
        String userIdFromToken = jwtTokenProvider.getUserInfoFromToken(token);
        System.out.println("userIdFromToken = " + userIdFromToken);
        return "hi";
    }

}

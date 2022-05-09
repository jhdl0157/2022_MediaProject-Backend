package com.example.timecapsule.account.service;


import com.example.timecapsule.account.dto.response.KakaoResponse;
import com.example.timecapsule.account.dto.response.MyAccountResponse;
import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.account.repository.AccountRepository;
import com.example.timecapsule.exception.NotFoundException;
import com.example.timecapsule.user.dto.response.TokenResponseDto;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserService userService;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoRestApiKey;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoRestSecretKey;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUrl;
    private final String KAKAO_URL = "https://kauth.kakao.com";
    private final String TOKEN_TYPE = "Bearer";
    private final String EMAIL_SUFFIX = "@gmail.com";

    public String getKakaoLoginUrl() {
        return KAKAO_URL + "/oauth/authorize?client_id=" + kakaoRestApiKey +
                "&redirect_uri=" + kakaoRedirectUrl +
                "&response_type=code";
    }

    public TokenResponseDto getKakaoLogin(final String token) {
       //HashMap<String, String> kakaoTokens = getKakaoTokens(token);
        KakaoResponse kakaoResponse = getKakaoUserInfo(token);

        String accountEmail = kakaoResponse.getKakao_account().getEmail();
        if (accountEmail == null || accountEmail.equals("")) {
            accountEmail = kakaoResponse.getId() + EMAIL_SUFFIX;
        }
        User accountForCheck = userService.findUserByUserEmail(accountEmail);
        if (accountForCheck != null) {
            // 기존 회원이라면 존재한다면 Token 값을 갱신하고 반환한다.
            //우리가 만든 jwt를 보내준다.
            return userService.makeToken(accountForCheck.getUserId());
        } else {
            // 존재하지 않는다면 회원 가입 시키고 반환한다.
            return  userService.makeToken(userService.register(kakaoResponse).getUserId());
        }
    }
    public HashMap<String, String> getKakaoTokens(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> httpBody = new LinkedMultiValueMap<>();
        httpBody.add("grant_type", "authorization_code");
        httpBody.add("client_id", kakaoRestApiKey);
        httpBody.add("redirect_uri", kakaoRedirectUrl);
        httpBody.add("code", code);
        httpBody.add("client_secret", kakaoRestSecretKey);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenReq = new HttpEntity<>(httpBody, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> tokenResEntity = restTemplate.exchange(KAKAO_URL + "/oauth/token", HttpMethod.POST, kakaoTokenReq, HashMap.class);

        return tokenResEntity.getBody();
    }
    public KakaoResponse getKakaoUserInfo(final String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization", TOKEN_TYPE + " " + accessToken);

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoReq = new HttpEntity<>(httpHeaders);
        ResponseEntity<KakaoResponse> userInfo = restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, kakaoUserInfoReq, KakaoResponse.class);

        return userInfo.getBody();
    }
    public MyAccountResponse updateTokenWithAccount(Long accountId, String accessToken) {
        Optional<Account> optionalExistAccount = accountRepository.findById(accountId);
        Account existAccount = optionalExistAccount.map(account -> {
            account.setAccessToken(accessToken);
            return account;
        }).orElseThrow(NotFoundException::new);

        accountRepository.save(existAccount);
        return MyAccountResponse.toAccountResponse(existAccount);
    }
}

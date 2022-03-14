package com.example.timecapsule.account.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
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
        return new StringBuilder()
                .append(KAKAO_URL).append("/oauth/authorize?client_id=").append(kakaoRestApiKey)
                .append("&redirect_uri=").append(kakaoRedirectUrl)
                .append("&response_type=code")
                .toString();
    }
}

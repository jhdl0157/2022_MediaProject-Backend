package com.example.timecapsule.account.dto.response;

import com.example.timecapsule.account.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MyAccountResponse {
    Long accountId;
    String accountEmail;
    String profileNickname;
    String userName;
    String profileImageUrl;
    String accessToken;
    Long kakaoId;
    LocalDateTime createdAt;


    public static MyAccountResponse toAccountResponse(Account account) {
        return MyAccountResponse.builder()
                .accessToken(account.getAccessToken())
                .profileNickname(account.getProfileNickname())
                .userName(account.getUserName())
                .accountEmail(account.getAccountEmail())
                .profileImageUrl(account.getProfileImageUrl())
                .accountId(account.getAccountId())
                .kakaoId(account.getKakaoId())
                .createdAt(account.getCreatedAt())
                .build();
    }
}

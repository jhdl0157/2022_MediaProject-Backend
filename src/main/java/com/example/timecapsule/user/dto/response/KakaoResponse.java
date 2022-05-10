package com.example.timecapsule.user.dto.response;

import lombok.Data;

@Data
public class KakaoResponse {

    private Long id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Data
    public class KakaoAccount {
        private Boolean profile_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;

        @Data
        public class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
        }
    }

    public Account toAccount(String accessToken) {
        Account account = new Account();
        if (this.kakao_account.email == null || this.kakao_account.email.equals(""))
            account.setAccountEmail(String.valueOf(this.id));
        else account.setAccountEmail(this.kakao_account.email);
        account.setProfileNickname(this.properties.nickname);
        account.setAccessToken(accessToken);
        account.setKakaoId(this.id);
        return account;
    }
}
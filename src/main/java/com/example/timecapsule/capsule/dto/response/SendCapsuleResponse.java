package com.example.timecapsule.capsule.dto.response;

import com.example.timecapsule.account.dto.response.MyAccountResponse;
import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.capsule.entity.Capsule;
import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalDateTime;

@Builder
@Data
public class SendCapsuleResponse {
    Long capsuleId;
    String title;
    String content;
    Long kakaoId;
    LocalDateTime duration;
    Point2D.Double location;

    public static SendCapsuleResponse toSendResponse(Capsule capsule) {
        return SendCapsuleResponse.builder()
                .capsuleId(capsule.getCapsuleId())
                .title(capsule.getCapsuleTitle())
                .content(capsule.getCapsuleContent())
                .kakaoId(capsule.getKakaoId())
                .duration(capsule.getDuration())
                .location(capsule.getLocation())
                .build();
    }

}

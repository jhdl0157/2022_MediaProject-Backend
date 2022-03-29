package com.example.timecapsule.capsule.dto.response;

import com.example.timecapsule.capsule.entity.Capsule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapsuleResponse {
    Long capsuleId;
    String title;
    String content;
    String nickname;
    Long recipient;
    LocalDateTime duration;
    Point2D.Double location;
    Boolean opened;
    Long sender;
    public static CapsuleResponse toCapsuleResponse(Capsule capsule) {
        return CapsuleResponse.builder()
                .capsuleId(capsule.getCapsuleId())
                .title(capsule.getCapsuleTitle())
                .content(capsule.getCapsuleContent())
                .nickname(capsule.getNickname())
                .recipient(capsule.getRecipient())
                .duration(capsule.getDuration())
                .location(capsule.getLocation())
                .sender(capsule.getSenderId())
                .opened(capsule.getIsOpened())
                .build();
    }

}

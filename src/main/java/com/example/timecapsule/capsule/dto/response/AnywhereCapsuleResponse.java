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
public class AnywhereCapsuleResponse {
    Long capsuleId;
    String content;
    String nickname;
    Long recipient;
    LocalDateTime duration;
    Point2D.Double location;
    Byte capsuleType;
    Boolean opened;
    String sender;
    public static AnywhereCapsuleResponse toCapsuleResponse(Capsule capsule) {
        return AnywhereCapsuleResponse.builder()
                .capsuleId(capsule.getCapsuleId())
                .content(capsule.getCapsuleContent())
                .nickname(capsule.getNickname())
                .recipient(capsule.getRecipient())
                .duration(capsule.getDuration())
                .location(capsule.getLocation())
                .capsuleType(capsule.getCapsuleType())
                .sender(capsule.getSenderId())
                .opened(capsule.getIsOpened())
                .build();
    }

}

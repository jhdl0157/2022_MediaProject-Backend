package com.example.timecapsule.capsule.dto.response;

import com.example.timecapsule.capsule.entity.Capsule;
import lombok.Builder;
import lombok.Data;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;

@Builder
@Data
public class CapsuleResponse {
    Long capsuleId;
    String title;
    String content;
    Long recipient;
    LocalDateTime duration;
    Point2D.Double location;

    public static CapsuleResponse toSendResponse(Capsule capsule) {
        return CapsuleResponse.builder()
                .capsuleId(capsule.getCapsuleId())
                .title(capsule.getCapsuleTitle())
                .content(capsule.getCapsuleContent())
                .recipient(capsule.getRecipient())
                .duration(capsule.getDuration())
                .location(capsule.getLocation())
                .build();
    }

}

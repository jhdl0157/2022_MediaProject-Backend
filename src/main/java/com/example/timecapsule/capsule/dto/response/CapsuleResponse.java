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
    Long recipient;
    LocalDateTime duration;
    Point2D.Double location;
    public static CapsuleResponse toCapsuleResponse(Capsule capsule) {
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

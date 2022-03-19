package com.example.timecapsule.capsule.dto.request;


import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.capsule.entity.Capsule;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CapsuleRequest {
    String title;
    String content;
    Long recipient;
    Long duration;
    Double latitude;
    Double longitude;

    public Capsule toCapsule(Account account, LocalDateTime currentDate) {
        Capsule capsule=new Capsule();
        capsule.addAccount(account);
        capsule.setCapsuleTitle(title);
        capsule.setCapsuleContent(content);
        capsule.setRecipient(recipient);
        capsule.setDuration(currentDate.plusDays(duration));
        capsule.setIsOpened(false);
        capsule.setLocation(capsule.setLocationFunc(latitude,longitude));
        return capsule;
    }
}

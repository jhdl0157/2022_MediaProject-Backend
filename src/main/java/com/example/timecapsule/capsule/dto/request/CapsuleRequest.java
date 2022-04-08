package com.example.timecapsule.capsule.dto.request;


import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.user.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CapsuleRequest {
    String title;
    String content;
    String nickname;
    Long recipient;
    Long duration;
    Double latitude;
    Double longitude;

    public Capsule toCapsule(Account account, LocalDateTime currentDate) {
        Capsule capsule=new Capsule();
        capsule.addAccount(account);
        capsule.setCapsuleTitle(title);
        capsule.setCapsuleContent(content);
        capsule.setNickname(nickname);
        capsule.setRecipient(recipient);
        capsule.setDuration(currentDate.plusDays(duration));
        capsule.setIsOpened(false);
        capsule.setLocation(capsule.setLocationFunc(latitude,longitude));
        capsule.setSenderId(account.getKakaoId());
        return capsule;
    }

    public Capsule toCapsule(User user, LocalDateTime currentDate) {
        Capsule capsules=new Capsule();
        capsules.addUser(user);
        capsules.setCapsuleTitle(title);
        capsules.setCapsuleContent(content);
        capsules.setNickname(nickname);
        capsules.setRecipient(recipient);
        capsules.setDuration(currentDate.plusDays(duration));
        capsules.setIsOpened(false);
        capsules.setLocation(capsules.setLocationFunc(latitude,longitude));
        capsules.setSenderId(user.getId());
        return capsules;
    }
}

package com.example.timecapsule.capsule.dto;


import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.capsule.entity.Capsule;
import lombok.Data;

@Data
public class CapsuleRequest {
    String title;
    String content;

    public Capsule toCapsule(Account account) {
        Capsule capsule=new Capsule();
        capsule.addAccount(account);
        capsule.setCapsuleContent(content);
        capsule.setCapsuleTitle(title);
        capsule.setIsOpened(false);
        return capsule;
    }
}

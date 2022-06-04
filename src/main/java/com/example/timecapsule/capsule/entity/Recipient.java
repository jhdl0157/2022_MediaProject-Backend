package com.example.timecapsule.capsule.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Recipient {
    private String recipientNickname;

    private Long recipientId;
    public Recipient() {}
    public Recipient(String recipientNickname, Long recipientId) {
        this.recipientNickname = recipientNickname;
        this.recipientId = recipientId;
    }

    public String getRecipientNickname() {
        return recipientNickname;
    }

    public Long getRecipientId() {
        return recipientId;
    }
    public void setRecipientNickname(String recipient) {
        this.recipientNickname = recipient;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

}

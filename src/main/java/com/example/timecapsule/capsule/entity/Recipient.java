package com.example.timecapsule.capsule.entity;

import javax.persistence.Embeddable;

@Embeddable

public class Recipient {
    private String recipient;
    private Long recipientId;

    public Recipient(String recipient, Long recipientId) {
        this.recipient = recipient;
        this.recipientId = recipientId;
    }
}

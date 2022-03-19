package com.example.timecapsule.capsule.entity;

import com.example.timecapsule.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Capsule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long capsuleId;
    String capsuleTitle;
    String capsuleContent;
    Boolean isOpened;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void addAccount(Account updateAccount) {
        this.setAccount(updateAccount);
    }
}

package com.example.timecapsule.capsule.entity;

import com.example.timecapsule.account.entity.Account;
import com.example.timecapsule.config.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Capsule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long capsuleId;
     String capsuleTitle;
     String capsuleContent;
     Boolean isOpened;
     Long recipient;
     String nickname;
     LocalDateTime duration;
     Point2D.Double location;
     Long senderId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    public Account account;

    public void addAccount(Account updateAccount) {
        this.setAccount(updateAccount);
    }
    public Point2D.Double setLocationFunc(double latitude, double longitude){
        Point2D.Double now=new Point2D.Double();
        log.info("위도: {}, 경도 {}",latitude,longitude);
        now.setLocation(latitude,longitude);
        log.info("----------------------------");
        log.info("Point 변환후 좌표 :{}",now.toString());
        return now;
    }
}

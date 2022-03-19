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
    private Long capsuleId;
    private String capsuleTitle;
    private String capsuleContent;
    private Boolean isOpened;
    private Long recipient;
    private LocalDateTime duration;
    private Point2D.Double location;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

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

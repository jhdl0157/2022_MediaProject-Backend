package com.example.timecapsule.capsule.entity;

import com.example.timecapsule.config.BaseEntity;
import com.example.timecapsule.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//TODO SET 사용 자제
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@Builder
public class Capsule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long capsuleId;
    private String capsuleContent;
    private Boolean isOpened;
    private String recipient;
    private String nickname;
    private LocalDateTime duration;
    private Point2D.Double location;
    private String senderId;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id")
    public User user;

    public void openCapsule(Capsule capsule) {
        capsule.isOpened = true;
    }

//    public void addAccount(Account updateAccount) {
//        this.setAccount(updateAccount);
//    }
//
//    public void addUser(User updateUser) {
//        this.setUser(updateUser);
//    }

}

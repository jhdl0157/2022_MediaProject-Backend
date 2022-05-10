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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class Capsule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long capsuleId;
     String capsuleTitle;
     String capsuleContent;
     Boolean isOpened;
     String recipient;
     String nickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate duration;
     Point2D.Double location;
     String senderId;
     Boolean locationCheck;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id")
    public User user;

    public void openCapsule(Capsule capsule){
        capsule.isOpened=true;
    }

//    public void addAccount(Account updateAccount) {
//        this.setAccount(updateAccount);
//    }
//
//    public void addUser(User updateUser) {
//        this.setUser(updateUser);
//    }

}

package com.example.timecapsule.user.entity;

import com.example.timecapsule.config.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Setter
@Entity
@NoArgsConstructor
public class Auth extends BaseEntity  {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String userId;
    private String refreshToken;
}

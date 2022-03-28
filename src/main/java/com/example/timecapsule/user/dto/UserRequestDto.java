package com.example.timecapsule.user.dto;

import com.example.timecapsule.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequestDto {
    private String userId;
    private String userPw;
}

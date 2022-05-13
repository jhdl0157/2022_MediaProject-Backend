package com.example.timecapsule.user.entity;

import com.example.timecapsule.exception.IdException;
import com.example.timecapsule.user.dto.response.KakaoResponse;
import com.example.timecapsule.capsule.entity.Capsule;
import com.example.timecapsule.config.BaseEntity;
import com.example.timecapsule.user.dto.request.UserRequestDto;
import com.example.timecapsule.user.dto.response.UserResponseDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userPw;
    private String userNickname;
    private String userEmail;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    List<Capsule> products = new ArrayList<>();
    //TODO 연관관계 맵핑
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public static User of (KakaoResponse kakaoResponse, String password){
        return User.builder()
                .userId(kakaoResponse.getId().toString())
                .userPw(password)
                .userNickname(kakaoResponse.getProperties().getNickname())
                .userEmail(kakaoResponse.getKakao_account().getEmail())
                .build();
    }

    public static User of(UserRequestDto userRequestDto,String password){
        return User.builder()
                .userId(userRequestDto.getUserId())
                .userPw(password)
                .userNickname(userRequestDto.getUserNickname())
                .userEmail(userRequestDto.getUserEmail())
                .build();
    }

    public static UserResponseDto toUserResponse (User user){
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .userNickname(user.getUserNickname())
                .build();
    }

}

package com.example.timecapsule.user.service;

import com.example.timecapsule.user.dto.TokenResponseDto;
import com.example.timecapsule.user.dto.UserRequestDto;
import com.example.timecapsule.user.entity.Auth;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.jwt.JwtTokenProvider;
import com.example.timecapsule.user.repository.AuthRepository;
import com.example.timecapsule.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRequestDto userRequestDto){
        String encodedPw = passwordEncoder.encode(userRequestDto.getUserPw());

        User user = User.builder()
                .userId(userRequestDto.getUserId())
                .userPw(encodedPw)
                .build();
        userRepository.save(user);
    }

    public TokenResponseDto login(UserRequestDto userRequestDto) throws Exception{
        User user = userRepository.findUserByUserId(userRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        if (!passwordEncoder.matches(userRequestDto.getUserPw(), user.getUserPw())){
            throw new Exception("Wrong password.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        Auth auth = Auth.builder()
                .userId(user.getUserId())
                .refreshToken(refreshToken)
                .build();
        authRepository.save(auth);

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }

}

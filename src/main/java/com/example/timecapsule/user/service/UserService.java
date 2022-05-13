package com.example.timecapsule.user.service;

import com.example.timecapsule.user.dto.response.KakaoResponse;
import com.example.timecapsule.exception.*;
import com.example.timecapsule.user.dto.response.TokenResponseDto;
import com.example.timecapsule.user.dto.request.UserRequestDto;
import com.example.timecapsule.user.dto.response.UserResponseDto;
import com.example.timecapsule.user.entity.Auth;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.jwt.JwtTokenProvider;
import com.example.timecapsule.user.repository.AuthRepository;
import com.example.timecapsule.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(UserRequestDto userRequestDto) {
        User user = User.of(userRequestDto, passwordEncoder.encode(userRequestDto.getUserPw()));
        userRepository.save(user);
        return User.toUserResponse(user);
    }

    public User register(KakaoResponse kakaoResponse) {
        if (kakaoResponse.getKakao_account().getEmail() == null || kakaoResponse.getKakao_account().getEmail().equals(""))
            kakaoResponse.getKakao_account().setEmail(kakaoResponse.getId() + "@gmail.com");
        User user = User.of(kakaoResponse, passwordEncoder.encode(kakaoResponse.getId() + "kakao"));
        userRepository.save(user);
        return user;
    }

    public void isUserIdDuplicated(String userId) {
        log.info("UserService.isUserIdDuplicated");
        if (userRepository.findUserByUserId(userId).isPresent()) {
            throw new DUPLICATEDATEXCEPTION();
        }
    }

    public void isUserNicknameDuplicated(String userNickname) {
        if (userRepository.findUserByUserNickname(userNickname).isPresent()) {
            throw new DUPLICATEDATEXCEPTION();
        }
    }

    public TokenResponseDto login(UserRequestDto userRequestDto) throws Exception {
        User user = userRepository.findUserByUserId(userRequestDto.getUserId()).orElseThrow(IDEXCEPTION::new);
        if (!passwordEncoder.matches(userRequestDto.getUserPw(), user.getUserPw())) {
            throw new PASSWORDEXCEPTION();
        }
        return makeToken(user);
    }

    public TokenResponseDto makeToken(final User user) {
        //TODO 여기서 중복으로 auth에 데이터가 쌓인다
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        Auth auth = Auth.builder()
                .userId(user.getUserId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        authRepository.save(auth);
        return TokenResponseDto.builder()
                .userId(user.getId())
                .userNickname(user.getUserNickname())
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public User findUserByAccessToken(String accessToken) {
//        jwtTokenProvider.getUserInfoFromToken(accessToken);
//        Auth auth=authRepository.findAuthByAccessToken(accessToken);
        log.info("유저의 정보는 : {}", jwtTokenProvider.getUserInfoFromToken(accessToken));
        return userRepository.findUserByUserId(jwtTokenProvider.getUserInfoFromToken(accessToken)).orElseThrow(NOTFOUNDEXCEPTION::new);
    }

    public User findUserByUserEmail(String email) {
        return userRepository.findUserByUserEmail(email).orElse(null);
    }

    public void deleteUser(Long userId, String accessToken) {
        //TODO 자바는 카멜케이스 명명규칙
        User nowuser = findUserByAccessToken(accessToken);
        //TODO 이런 분기는 좋지 않음 최대한 ifelse를 없애기
        if (nowuser.getId().equals(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new IDEXCEPTION();
        }
    }

}

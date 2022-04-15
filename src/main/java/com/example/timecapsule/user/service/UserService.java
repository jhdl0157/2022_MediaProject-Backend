package com.example.timecapsule.user.service;

import com.example.timecapsule.account.dto.response.KakaoResponse;
import com.example.timecapsule.exception.NotFoundException;
import com.example.timecapsule.exception.NotFoundUserException;
import com.example.timecapsule.user.dto.TokenResponseDto;
import com.example.timecapsule.user.dto.UserRequestDto;
import com.example.timecapsule.user.entity.Auth;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.jwt.JwtTokenProvider;
import com.example.timecapsule.user.repository.AuthRepository;
import com.example.timecapsule.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public User register(UserRequestDto userRequestDto){
        String encodedPw = passwordEncoder.encode(userRequestDto.getUserPw());

        User user = User.builder()
                .userId(userRequestDto.getUserId())
                .userPw(encodedPw)
                .userNickname(userRequestDto.getUserNickname())
                .userEmail(userRequestDto.getUserEmail())
                .build();
        userRepository.save(user);
        return user;
    }
    public User register(KakaoResponse kakaoResponse){
        String encodedPw = passwordEncoder.encode(kakaoResponse.getId()+"kakao");
        if(kakaoResponse.getKakao_account().getEmail()==null || kakaoResponse.getKakao_account().getEmail().equals("") )
            kakaoResponse.getKakao_account().setEmail(kakaoResponse.getId()+"@gmail.com");
        User user = User.builder()
                .userId(kakaoResponse.getId().toString())
                .userPw(encodedPw)
                .userNickname(kakaoResponse.getProperties().getNickname())
                .userEmail(kakaoResponse.getKakao_account().getEmail())
                .build();
        userRepository.save(user);
        return user;
    }
    public boolean isUserIdDuplicated (String userId){
        Optional<User> user = userRepository.findUserByUserId(userId);
        return user.isPresent();
    }

    public boolean isUserNicknameDuplicated (String userNickname){
        Optional<User> user = userRepository.findUserByUserNickname(userNickname);
        return user.isPresent();
    }

    public TokenResponseDto login(UserRequestDto userRequestDto) throws Exception{
        User user = userRepository.findUserByUserId(userRequestDto.getUserId())
                //변경후
                .orElseThrow(NotFoundUserException::new);

        //변경전
//                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));
        //윗부분 예외처리

        if (!passwordEncoder.matches(userRequestDto.getUserPw(), user.getUserPw())){
            throw new Exception("Wrong password.");
        }
        //Todo 이거 함수로 빼서 재사용 하기 카카오쪽에서
        return makeToken(user);
    }
    public TokenResponseDto makeToken(User user){
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
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }
    @Transactional(readOnly = true)
    public User findUserByAccessToken(String accessToken) {
//        jwtTokenProvider.getUserInfoFromToken(accessToken);
//        Auth auth=authRepository.findAuthByAccessToken(accessToken);
        log.info("유저의 정보는 : {}",jwtTokenProvider.getUserInfoFromToken(accessToken));
        return userRepository.findUserByUserId(jwtTokenProvider.getUserInfoFromToken(accessToken)).orElseThrow(NotFoundException::new);
    }

    public User findUserByUserEmail(String email){
        return userRepository.findUserByUserEmail(email).orElse(null);
    }


}

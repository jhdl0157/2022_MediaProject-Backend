package com.example.timecapsule.user.service;

import com.example.timecapsule.account.dto.response.KakaoResponse;
import com.example.timecapsule.capsule.entity.Capsule;
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

    public UserResponseDto register(UserRequestDto userRequestDto){
        User user=User.of(userRequestDto,passwordEncoder.encode(userRequestDto.getUserPw()));
        userRepository.save(user);
        return User.toUserResponse(user);
    }
    public UserResponseDto register(KakaoResponse kakaoResponse){
        if(kakaoResponse.getKakao_account().getEmail()==null || kakaoResponse.getKakao_account().getEmail().equals("") )
            kakaoResponse.getKakao_account().setEmail(kakaoResponse.getId()+"@gmail.com");
        User user=User.of(kakaoResponse,passwordEncoder.encode(kakaoResponse.getId()+"kakao"));
        userRepository.save(user);
        return User.toUserResponse(user);
    }
    public void isUserIdDuplicated (String userId){
        log.info("UserService.isUserIdDuplicated");
        if(userRepository.findUserByUserId(userId).isPresent()) {
            throw new DuplicateDATAException();
        }
    }

    public void isUserNicknameDuplicated (String userNickname){
        if(userRepository.findUserByUserNickname(userNickname).isPresent()) {
            throw new DuplicateDATAException();
        }
    }

    public TokenResponseDto login(UserRequestDto userRequestDto) throws Exception{
        User user = userRepository.findUserByUserId(userRequestDto.getUserId()).orElseThrow(IdException::new);
        if (!passwordEncoder.matches(userRequestDto.getUserPw(), user.getUserPw())){
            throw new PasswordException();
        }
        return issueToken(user.getUserId());
    }
    public TokenResponseDto issueToken(final String userid){
        String accessToken = jwtTokenProvider.createAccessToken(userid);
        String refreshToken = jwtTokenProvider.createRefreshToken(userid);

        Auth authFromRepository = authRepository.findAuthByUserId(userid);
        
        if (authFromRepository != null) {
            authRepository.updateAuth(userid, accessToken, refreshToken);
        }
        else{
            Auth auth = Auth.builder()
                    .userId(userid)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            authRepository.save(auth);
        }


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

package com.example.timecapsule.search.service;

import com.example.timecapsule.search.dto.UserSearchResponseDto;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {
    private final UserRepository userRepository;

    public List<UserSearchResponseDto> getAllUsersWithKeyword(String keyword){
        List<User> allUserWithKeyword = userRepository.findByUserNicknameContaining(keyword);
        List<UserSearchResponseDto> returnList = new ArrayList<>();
        for (User user : allUserWithKeyword){
            if (user.getUserNickname().contains(keyword)) {
                returnList.add(UserSearchResponseDto.toUserSearchResponseDto(user));
            }
        }
        return returnList;
    }
    //Stream 사용
    public List<UserSearchResponseDto> searchNickname(String keyword){
        log.info("Search Service: searchNickname success");
        return userRepository.findByUserNicknameContainsIgnoreCase(keyword).stream().map(UserSearchResponseDto::toUserSearchResponseDto).collect(Collectors.toList());
    }

}

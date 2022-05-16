package com.example.timecapsule.search.service;

import com.example.timecapsule.search.dto.UserSearchResponseDto;
import com.example.timecapsule.user.entity.User;
import com.example.timecapsule.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class SearchService {
    private UserRepository userRepository;
    private UserSearchResponseDto userSearchResponseDto;

    public List<UserSearchResponseDto> getAllUsersWithKeyword(String keyword){
        List<User> allUser = userRepository.findAll();
        List<UserSearchResponseDto> returnList = new ArrayList<UserSearchResponseDto>();
        for (User user : allUser){
            if (user.getUserNickname().contains(keyword)) {
                returnList.add(userSearchResponseDto.toUserSearchResponseDto(user));
            }
        }
        return returnList;
    }

}

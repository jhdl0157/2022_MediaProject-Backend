package com.example.timecapsule.search.controller;

import com.example.timecapsule.main.common.ListResult;
import com.example.timecapsule.main.common.service.ResponseService;
import com.example.timecapsule.search.dto.UserSearchResponseDto;
import com.example.timecapsule.search.service.SearchService;
import com.example.timecapsule.user.dto.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
public class SearchController {
    //private SearchService searchService;
    //private ResponseService responseService;

    @GetMapping("/api/search")
    public ResponseEntity<ListResult<UserSearchResponseDto>> getUsersByNicknameKeyword(@RequestParam String keyword){
        //List<UserSearchResponseDto> usersWithKeyword = searchService.getAllUsersWithKeyword(keyword);
        //return new ResponseEntity<>(responseService.getListResult(usersWithKeyword), HttpStatus.OK);
        return null;
    }

}

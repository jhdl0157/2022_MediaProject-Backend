package com.example.timecapsule.search.controller;

import com.example.timecapsule.main.common.CommonResult;
import com.example.timecapsule.main.common.ListResult;
import com.example.timecapsule.main.common.SingleResult;
import com.example.timecapsule.main.common.service.ResponseService;
import com.example.timecapsule.search.dto.UserSearchResponseDto;
import com.example.timecapsule.search.service.SearchService;
import com.example.timecapsule.user.dto.response.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;
    private final ResponseService responseService;

    @GetMapping("/api/search")
    public ResponseEntity<ListResult<UserSearchResponseDto>> getUsersByNicknameKeyword(@RequestParam String keyword){
        return new ResponseEntity<>(responseService.getListResult(searchService.getAllUsersWithKeyword(keyword)), HttpStatus.OK);
    }

    @GetMapping("/api/search/enabled")
    public ResponseEntity<CommonResult> getUserSearchEnabled(@RequestHeader("X-AUTH-TOKEN") String accessToken, @RequestParam Integer enabled){
        searchService.getUserSearchEnabled(accessToken, enabled);
        return new ResponseEntity<>(responseService.getSuccessResult(),HttpStatus.OK);
    }

}

package com.example.timecapsule.config;

import com.example.timecapsule.exception.NOT;
import com.example.timecapsule.exception.NotFoundException;
import com.example.timecapsule.exception.NotFoundUserException;
import com.example.timecapsule.main.common.CommonResponse;
import com.example.timecapsule.main.common.CommonResult;
import com.example.timecapsule.main.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final ResponseService responseService;
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResult> NotFoundException(NotFoundException e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.NOTFOUND);
        return new ResponseEntity<>(commonResult, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<CommonResult> NotFoundUserException(NotFoundUserException e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.NOTFOUND);
        return new ResponseEntity<>(commonResult, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NOT.class)
    public ResponseEntity<CommonResult> NOT(NOT e) {
        CommonResult commonResult = responseService.getFailResult(CommonResponse.NOT);
        return new ResponseEntity<>(commonResult, HttpStatus.EXPECTATION_FAILED);
    }
}

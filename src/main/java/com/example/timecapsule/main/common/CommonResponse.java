package com.example.timecapsule.main.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(0, "성공하였습니다."),
    FAIL(-1, "실패하였습니다."),
    INTERNALSERVERERROR(-500, "서버 에러");
    private final int code;
    private final String msg;
}

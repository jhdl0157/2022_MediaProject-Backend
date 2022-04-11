package com.example.timecapsule.main.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(0, "성공하였습니다."),
    FAIL(-1, "실패하였습니다."),
    INTERNALSERVERERROR(-500, "서버 에러"),
    NOTFOUND(-401,"찾기 오류"),
    NOTFOUNDUSER(-501,"찾는 유저가 없음"),
    NOT(-2012,"이건 오류");
    private final int code;
    private final String msg;
}

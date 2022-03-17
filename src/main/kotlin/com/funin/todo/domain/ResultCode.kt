package com.funin.todo.domain

enum class ResultCode(val message: String) {
    SUCCESS("성공"),
    INTERNAL_SERVER_ERROR("서버 에러"),
    BAD_REQUEST("요청에 오류가 있습니다"),

    // Authorization
    UNAUTHORIZED("인증 실패"),
    FORBIDDEN("권한 없음"),

    // User
    USER_NICKNAME_ALREADY_EXIST("중복된 유저 이름입니다"),
    USER_NOT_FOUND("유저를 찾을 수 없습니다."),
}
package com.funin.todo.domain

enum class ResultCode(val message: String) {
    SUCCESS("성공"),
    UNAUTHORIZED("인증 실패"),
    INTERNAL_SERVER_ERROR("서버 에러"),
    BAD_REQUEST("요청에 오류가 있습니다"),

    // User
    USER_NICKNAME_ALREADY_EXIST("중복된 유저 이름입니다")
}
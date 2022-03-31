package com.funin.todo.domain.exception

import com.funin.todo.domain.ResultCode

class UserNotFoundException(
    override val message: String? = ResultCode.USER_NOT_FOUND.message,
    override val cause: Throwable? = null
) : NotFoundException(
    resultCode = ResultCode.USER_NOT_FOUND,
    message = message,
    cause = cause
) {
    constructor(userId: Long) : this(message = "회원정보가 없습니다. userId: $userId")
}
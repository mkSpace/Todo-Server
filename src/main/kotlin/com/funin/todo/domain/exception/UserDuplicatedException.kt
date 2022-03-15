package com.funin.todo.domain.exception

import com.funin.todo.domain.ResultCode

class UserDuplicatedException(
    override val message: String?,
    override val cause: Throwable?
) : BusinessException(
    resultCode = ResultCode.USER_NICKNAME_ALREADY_EXIST,
    message = message,
    cause = cause
) {
    constructor(nickname: String) : this(
        message = "${ResultCode.USER_NICKNAME_ALREADY_EXIST.message}. nickname : $nickname",
        cause = null
    )
}
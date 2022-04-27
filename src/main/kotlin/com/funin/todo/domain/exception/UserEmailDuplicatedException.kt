package com.funin.todo.domain.exception

import com.funin.todo.domain.ResultCode

class UserEmailDuplicatedException(
    override val message: String?,
    override val cause: Throwable?
) : BusinessException(
    resultCode = ResultCode.USER_EMAIL_ALREADY_EXIST,
    message = message,
    cause = cause
) {
    constructor(email: String) : this(
        message = "${ResultCode.USER_EMAIL_ALREADY_EXIST.message}. email : $email",
        cause = null
    )
}
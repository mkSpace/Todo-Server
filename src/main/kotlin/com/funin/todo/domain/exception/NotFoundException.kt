package com.funin.todo.domain.exception

import com.funin.todo.domain.ResultCode

open class NotFoundException(
    override val resultCode: ResultCode,
    override val message: String?,
    override val cause: Throwable?
) : BusinessException(resultCode = resultCode, message = message, cause = cause)
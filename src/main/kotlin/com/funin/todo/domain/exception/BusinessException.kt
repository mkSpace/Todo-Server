package com.funin.todo.domain.exception

import com.funin.todo.domain.ResultCode

open class BusinessException(
    open val resultCode: ResultCode,
    override val message: String? = null,
    override val cause: Throwable? = null
): RuntimeException(message, cause)
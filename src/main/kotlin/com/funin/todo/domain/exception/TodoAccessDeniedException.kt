package com.funin.todo.domain.exception

import com.funin.todo.domain.ResultCode

class TodoAccessDeniedException(
    override val message: String? = ResultCode.TODO_ACCESS_DENIED.message,
    override val cause: Throwable? = null
) : BusinessException(
    resultCode = ResultCode.TODO_ACCESS_DENIED,
    message = message,
    cause = cause
)
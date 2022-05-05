package com.funin.todo.domain.exception

import com.funin.todo.domain.ResultCode

class TodoNotFoundException(
    override val message: String? = ResultCode.TODO_NOT_FOUND.message,
    override val cause: Throwable? = null
) : NotFoundException(
    resultCode = ResultCode.TODO_NOT_FOUND,
    message = message,
    cause = cause
) {
    constructor(todoId: Long) : this(message = "Todo를 찾을 수 없습니다. todoId: $todoId")
}
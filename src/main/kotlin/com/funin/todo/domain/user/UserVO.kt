package com.funin.todo.domain.user

import java.time.LocalDateTime

data class UserVO(val nickname: String, val createdAt: LocalDateTime)

fun User.toUserVO(): UserVO = UserVO(
    nickname = nickname
        ?: throw IllegalArgumentException("[${javaClass.simpleName}] User's nickname must not be null"),
    createdAt = createdAt
)
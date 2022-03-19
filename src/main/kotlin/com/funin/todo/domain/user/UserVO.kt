package com.funin.todo.domain.user

import java.time.LocalDateTime

data class UserVO(val id: Long, val nickname: String, val createdAt: LocalDateTime)

fun User.toUserVO(): UserVO? {
    val userId = id ?: return null
    return UserVO(
        id = userId,
        nickname = nickname
            ?: throw IllegalArgumentException("[${javaClass.simpleName}] User's nickname must not be null"),
        createdAt = createdAt
    )
}
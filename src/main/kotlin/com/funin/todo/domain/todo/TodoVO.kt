package com.funin.todo.domain.todo

import java.time.LocalDateTime

data class TodoVO(
    val id: Long,
    val content: String,
    val authorId: Long,
    val state: State,
    val reason: String? = null,
    val canUpdate: Boolean,
    val createdAt: LocalDateTime
)

fun Todo.toTodoVO(): TodoVO? {
    val todoId = id ?: return null
    val authorId = author?.id ?: return null
    return TodoVO(
        id = todoId,
        content = content ?: "",
        authorId = authorId,
        state = state,
        reason = reason,
        canUpdate = canUpdate,
        createdAt = createdAt
    )
}
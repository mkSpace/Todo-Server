package com.funin.todo.presentation.api.todo

import com.funin.todo.domain.todo.State

data class TodoRequest(
    val content: String = "",
    val state: State = State.NONE,
    val reason: String? = null,
    val canUpdate: Boolean = true
)

data class TodoResponse(
    val todoId: Long,
    val authorId: Long,
    val content: String,
    val state: State,
    val reason: String? = null,
    val canUpdate: Boolean
)
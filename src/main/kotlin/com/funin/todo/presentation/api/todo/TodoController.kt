package com.funin.todo.presentation.api.todo

import com.funin.todo.domain.todo.TodoService
import com.funin.todo.presentation.api.ApiResponse
import com.funin.todo.presentation.api.ApiV1
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1
class TodoController(private val todoService: TodoService) {

    @PostMapping("/todo")
    fun createTodo(
        @ModelAttribute("userId") userId: Long,
        @RequestBody request: TodoRequest
    ): ApiResponse<TodoResponse> {
        val todoVO = todoService.create(userId, request.content, request.reason, request.state, request.canUpdate)
            ?: throw IllegalStateException("Cannot create todo")
        return ApiResponse.success(
            data = TodoResponse(
                todoId = todoVO.id,
                authorId = todoVO.authorId,
                content = todoVO.content,
                state = todoVO.state,
                reason = todoVO.reason,
                canUpdate = todoVO.canUpdate,
                createdAt = todoVO.createdAt
            )
        )
    }

    @PostMapping("/todo/{todoId}/update")
    fun updateTodo(
        @ModelAttribute("userId") userId: Long,
        @PathVariable("todoId") todoId: Long,
        @RequestBody request: TodoRequest
    ): ApiResponse<TodoResponse> {
        val todoVO = todoService.update(
            todoId = todoId,
            userId = userId,
            content = request.content,
            reason = request.reason,
            state = request.state,
            canUpdate = request.canUpdate
        ) ?: throw IllegalStateException("Cannot update todo")
        return ApiResponse.success(
            data = TodoResponse(
                todoId = todoVO.id,
                authorId = todoVO.authorId,
                content = todoVO.content,
                state = todoVO.state,
                reason = todoVO.reason,
                canUpdate = todoVO.canUpdate,
                createdAt = todoVO.createdAt
            )
        )
    }
}
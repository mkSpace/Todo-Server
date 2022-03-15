package com.funin.todo.presentation.api.user

import com.funin.todo.domain.user.UserService
import com.funin.todo.presentation.api.ApiResponse
import com.funin.todo.presentation.api.ApiV1
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun signup(@RequestBody @Validated request: SignUpRequest): ApiResponse<SignUpResponse> {
        if (request.nickname.isNullOrBlank()) throw IllegalArgumentException("nickname must not be blank")
        if (request.password.isNullOrBlank()) throw IllegalArgumentException("password must not be blank")
        val response = userService.join(request.nickname, request.password).toSignUnResponse()
        return ApiResponse.success(response)
    }
}
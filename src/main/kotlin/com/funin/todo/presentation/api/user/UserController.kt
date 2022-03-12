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
        val nickname = request.nickname
            ?: throw IllegalArgumentException("nickname must not be null")
        val password = request.password ?: throw IllegalArgumentException("password must not be null")
        val response = userService.join(nickname, password).toSignUnResponse()
        return ApiResponse.success(response)
    }
}
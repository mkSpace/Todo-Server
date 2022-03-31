package com.funin.todo.presentation.api.user

import com.funin.todo.application.TokenService
import com.funin.todo.domain.user.UserService
import com.funin.todo.presentation.api.ApiResponse
import com.funin.todo.presentation.api.ApiV1
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1
class UserController(
    private val userService: UserService,
    private val jwtService: TokenService<Long>
) {

    @PostMapping("/signup")
    fun signup(@RequestBody @Validated request: SignUpRequest): ApiResponse<SignUpResponse> {
        val userVO = userService.join(request.email, request.nickname, request.password)
            ?: throw IllegalStateException("cannot create user")
        return ApiResponse.success(
            data = SignUpResponse(jwtService.encode(userVO.id), userVO.nickname)
        )
    }
}
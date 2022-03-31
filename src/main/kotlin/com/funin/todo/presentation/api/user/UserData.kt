package com.funin.todo.presentation.api.user

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignUpRequest(
    @field:Email @field:NotBlank val email: String,
    @field:NotBlank val nickname: String,
    @field:NotBlank val password: String
)

data class SignUpResponse(val accessToken: String, val nickname: String)

package com.funin.todo.presentation.api.user

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignUpRequest(
    @field:Email @field:NotBlank val email: String,
    @field:NotBlank val nickname: String,
    @field:NotBlank val password: String
)

data class SimpleUserResponse(
    val id: Long,
    val accessToken: String,
    val nickname: String,
    val profileImage: String? = null
)

data class SignInRequest(@field:Email @field:NotBlank val email: String, @field:NotBlank val password: String)
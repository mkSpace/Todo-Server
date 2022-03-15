package com.funin.todo.presentation.api.user

import com.funin.todo.domain.user.UserVO
import javax.validation.constraints.NotBlank

data class SignUpRequest(@field:NotBlank val nickname: String?, @field:NotBlank val password: String?)

data class SignUpResponse(val nickname: String)

fun UserVO.toSignUnResponse(): SignUpResponse = SignUpResponse(nickname)
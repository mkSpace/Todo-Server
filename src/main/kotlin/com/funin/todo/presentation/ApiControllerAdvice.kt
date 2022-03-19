package com.funin.todo.presentation

import com.funin.todo.domain.ResultCode
import com.funin.todo.domain.exception.BusinessException
import com.funin.todo.presentation.api.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.security.Principal

@RestControllerAdvice
class ApiControllerAdvice {

    companion object {
        val log: Logger = LoggerFactory.getLogger(ApiControllerAdvice::class.java)
    }

    @ModelAttribute("userId")
    fun resolveUserId(principal: Principal?): Long? {
        log.debug("principal : {}", principal)
        if (principal is UsernamePasswordAuthenticationToken) {
            if (principal.principal is User) {
                return (principal.principal as? User)?.username?.toLongOrNull()
            }
            return principal.principal.toString().toLongOrNull()
        }
        return null
    }

    /**
     * Server 내 오류가 있는 경우
     */
    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleIllegalStateException(e: IllegalStateException): ApiResponse<Unit> {
        log.error("IllegalStateException", e)
        return ApiResponse.failure(ResultCode.INTERNAL_SERVER_ERROR)
    }

    /**
     * 요청에 오류가 있는 경우 (광범위)
     */
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ApiResponse<Unit> {
        log.error("IllegalArgumentException", e)
        return ApiResponse.failure(ResultCode.BAD_REQUEST)
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleBusinessException(e: BusinessException): ApiResponse<Unit> {
        log.error("BusinessException", e.cause)
        return ApiResponse.failure(e.resultCode)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ApiResponse<Unit> {
        log.error("MethodArgumentNotValidException", e)
        return ApiResponse.failure(ResultCode.BAD_REQUEST)
    }
}
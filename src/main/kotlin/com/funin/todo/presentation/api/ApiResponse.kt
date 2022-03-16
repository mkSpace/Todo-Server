package com.funin.todo.presentation.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.funin.todo.domain.ResultCode

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T? = null
) {

    companion object {
        fun <T> success(data: T?): ApiResponse<T> =
            ApiResponse(ResultCode.SUCCESS.name, ResultCode.SUCCESS.message, data)

        fun <T> success(): ApiResponse<T> = success(null)
        fun <T> failure(code: String, message: String): ApiResponse<T> = ApiResponse(code, message, null)
        fun <T> failure(resultCode: ResultCode): ApiResponse<T> = ApiResponse(resultCode.name, resultCode.message, null)
    }
}
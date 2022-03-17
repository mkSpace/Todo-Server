package com.funin.todo.application

interface TokenService<T> {
    fun encode(userId: T): String
    fun decode(token: String?): T?
}
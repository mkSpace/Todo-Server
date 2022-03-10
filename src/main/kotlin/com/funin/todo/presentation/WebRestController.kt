package com.funin.todo.presentation

import com.funin.todo.domain.user.User
import com.funin.todo.domain.user.UserRepository
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class WebRestController(
    private val environment: Environment,
    private val userRepository: UserRepository
) {

    @GetMapping("/profile")
    fun getProfile(): String {
        return environment.activeProfiles.firstOrNull() ?: ""
    }

    @GetMapping("/funin")
    fun test(@RequestParam username: String): User {
        return userRepository.save(username)
    }
}
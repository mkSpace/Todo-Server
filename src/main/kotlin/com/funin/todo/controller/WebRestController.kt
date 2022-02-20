package com.funin.todo.controller

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WebRestController(private val environment: Environment) {

    @GetMapping("/profile")
    fun getProfile(): String {
        return environment.activeProfiles.firstOrNull() ?: ""
    }
}
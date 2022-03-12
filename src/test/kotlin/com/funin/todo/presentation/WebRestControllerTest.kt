package com.funin.todo.presentation

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class WebRestControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun check_profile() {
        val profile = restTemplate.getForObject("/profile", String::class.java)
        Assertions.assertThat(profile).isEqualTo("local")
    }
}
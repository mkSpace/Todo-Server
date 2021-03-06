package com.funin.todo.presentation

import com.funin.todo.ActiveTestProfile
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveTestProfile
internal class WebRestControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun check_profile() {
        val profile = restTemplate.getForObject("/profile", String::class.java)
        Assertions.assertThat(profile).isEqualTo("test")
    }
}
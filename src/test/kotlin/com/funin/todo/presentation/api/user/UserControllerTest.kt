package com.funin.todo.presentation.api.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.funin.todo.ActiveTestProfile
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@ActiveTestProfile
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    lateinit var userController: UserController

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun 회원가입_성공() {
        // given
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()
        val request = SignUpRequest(nickname, password)

        mockMvc
            .perform(
                post("/api/v1/signup")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.data.accessToken").exists())
            .andExpect(jsonPath("\$.data.nickname").value(nickname))
    }

    @Test
    fun 회원가입_빈_닉네임_실패() {
        val nickname = ""
        val password = UUID.randomUUID().toString()
        val request = SignUpRequest(nickname, password)

        assertThrows(IllegalArgumentException::class.java) {
            userController.signup(request)
        }
    }

    @Test
    fun 회원가입_빈_패스워드_실패() {
        val nickname = "funin-todo"
        val password = ""
        val request = SignUpRequest(nickname, password)

        assertThrows(IllegalArgumentException::class.java) {
            userController.signup(request)
        }
    }

    @Test
    fun 회원가입_빈_닉네임_패스워드_실패() {
        val request = SignUpRequest("", "")

        assertThrows(IllegalArgumentException::class.java) {
            userController.signup(request)
        }
    }
}
package com.funin.todo.presentation.api.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.funin.todo.ActiveTestProfile
import com.funin.todo.domain.ResultCode
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
        val email = "funin@google.com"
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()
        val request = SignUpRequest(email, nickname, password)

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
        val email = "funin@google.com"
        val nickname = ""
        val password = UUID.randomUUID().toString()
        val request = SignUpRequest(email, nickname, password)

        mockMvc
            .perform(
                post("/api/v1/signup")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("\$.code").value(ResultCode.BAD_REQUEST.name))
            .andExpect(jsonPath("\$.message").value(ResultCode.BAD_REQUEST.message))
    }

    @Test
    fun 회원가입_이메일_형식_실패() {
        val email = "funin"
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()
        val request = SignUpRequest(email, nickname, password)

        mockMvc
            .perform(
                post("/api/v1/signup")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("\$.code").value(ResultCode.BAD_REQUEST.name))
            .andExpect(jsonPath("\$.message").value(ResultCode.BAD_REQUEST.message))
    }

    @Test
    fun 회원가입_빈_이메일_닉네임_패스워드_실패() {
        val request = SignUpRequest("", "", "")

        mockMvc
            .perform(
                post("/api/v1/signup")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("\$.code").value(ResultCode.BAD_REQUEST.name))
            .andExpect(jsonPath("\$.message").value(ResultCode.BAD_REQUEST.message))
    }

    @Test
    fun 로그인_성공() {
        // given
        val email = "funin@google.com"
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()
        val signUpRequest = SignUpRequest(email, nickname, password)
        val loginRequest = SignInRequest(email, password)
        userController.signup(signUpRequest)

        mockMvc
            .perform(
                post("/api/v1/login")
                    .content(objectMapper.writeValueAsString(loginRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.data.accessToken").exists())
            .andExpect(jsonPath("\$.data.nickname").value(nickname))
    }

    @Test
    fun 로그인_이메일_형식_오류_실패() {
        // given
        val email = "funin@google.com"
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()
        val signUpRequest = SignUpRequest(email, nickname, password)
        val loginRequest = SignInRequest("funin", password)
        userController.signup(signUpRequest)

        mockMvc
            .perform(
                post("/api/v1/login")
                    .content(objectMapper.writeValueAsString(loginRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("\$.code").value(ResultCode.BAD_REQUEST.name))
            .andExpect(jsonPath("\$.message").value(ResultCode.BAD_REQUEST.message))
    }

    @Test
    fun 로그인_패스워드_불일치_실패() {
        // given
        val email = "funin@google.com"
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()
        val signUpRequest = SignUpRequest(email, nickname, password)
        val loginRequest = SignInRequest(email, UUID.randomUUID().toString())
        userController.signup(signUpRequest)

        mockMvc
            .perform(
                post("/api/v1/login")
                    .content(objectMapper.writeValueAsString(loginRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().is5xxServerError)
            .andExpect(jsonPath("\$.code").value(ResultCode.USER_NOT_FOUND.name))
            .andExpect(jsonPath("\$.message").value("비밀번호가 일치하지 않습니다."))
    }
}
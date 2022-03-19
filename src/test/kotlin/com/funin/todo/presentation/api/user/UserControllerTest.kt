package com.funin.todo.presentation.api.user

import com.funin.todo.domain.user.UserRepository
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class UserControllerTest {

    @Autowired
    lateinit var userController: UserController

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun beforeEach() {
        userRepository.deleteAll()
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
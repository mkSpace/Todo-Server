package com.funin.todo.domain.user

import com.funin.todo.domain.exception.UserDuplicatedException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
internal class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @AfterEach
    fun afterEach() {
        userRepository.deleteAll()
    }

    @Test
    fun join_성공() {
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()

        val userVO = userService.join(nickname, password)
        val findMember = userRepository.findByNickname(nickname)

        assertThat(userVO.nickname).isEqualTo(findMember?.nickname)
        assertThat(findMember?.password).isNotNull
        assertThat(findMember?.salt).isNotNull
    }

    @Test
    fun join_중복시_실패() {
        val nickname = "funin1"
        val password = UUID.randomUUID().toString()

        userService.join(nickname, password)

        Assertions.assertThrows(UserDuplicatedException::class.java) {
            userService.join(nickname, password)
        }
    }
}
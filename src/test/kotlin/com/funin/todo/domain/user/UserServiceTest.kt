package com.funin.todo.domain.user

import com.funin.todo.ActiveTestProfile
import com.funin.todo.domain.exception.UserDuplicatedException
import com.funin.todo.presentation.utils.CipherManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
@ActiveTestProfile
internal class UserServiceTest {
    @Spy
    lateinit var cipherManager: CipherManager

    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var userService: UserServiceImpl

    @Test
    fun join_성공() {
        //given
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()
        val simpleSalt = cipherManager.generateSalt()
        val simpleUser = User().apply {
            this.id = 1L
            this.nickname = nickname
            this.password = password
            this.salt = simpleSalt
            this.createdAt = LocalDateTime.now()
        }
        `when`(userRepository.findByNickname(nickname)).thenReturn(null)
        `when`(cipherManager.generateSalt()).thenReturn(simpleSalt)
        `when`(cipherManager.encodeSHA256(password, simpleSalt)).thenReturn(password)
        `when`(userRepository.save(any())).thenReturn(simpleUser)

        //when
        val userVO = userService.join(nickname, password)

        //then
        verify(userRepository).save(any())
        assertThat(userVO).isNotNull
        assertThat(userVO?.nickname).isEqualTo(nickname)
    }

    @Test
    fun join_중복시_실패() {
        //given
        val nickname = "funin-todo"
        val password = UUID.randomUUID().toString()
        val salt = cipherManager.generateSalt()
        doAnswer {
            User().apply {
                this.nickname = nickname
                this.password = cipherManager.encodeSHA256(password, salt)
                this.salt = salt
            }
        }.`when`(userRepository).findByNickname(nickname)

        //then
        Assertions.assertThrows(UserDuplicatedException::class.java) {
            userService.join(nickname, password)
        }
    }
}
package com.funin.todo.domain.todo

import com.funin.todo.ActiveTestProfile
import com.funin.todo.domain.exception.TodoAccessDeniedException
import com.funin.todo.domain.exception.TodoNotFoundException
import com.funin.todo.domain.exception.UserNotFoundException
import com.funin.todo.domain.user.User
import com.funin.todo.domain.user.UserRepository
import com.funin.todo.generateSimpleAbsoluteLong
import com.funin.todo.generateSimpleString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
@ActiveTestProfile
class TodoServiceTest {

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var todoRepository: TodoRepository

    @InjectMocks
    lateinit var todoService: TodoServiceImpl

    @Test
    fun create_성공() {
        // given
        val todoId = generateSimpleAbsoluteLong()
        val content = generateSimpleString()
        val authorId = generateSimpleAbsoluteLong()
        val simpleUser = User().apply { id = authorId }
        val state = State.NONE
        val reason = generateSimpleString()
        val canUpdate = true
        val simpleTodo = Todo().apply {
            this.id = todoId
            this.content = content
            this.author = simpleUser
            this.state = state
            this.reason = reason
            this.canUpdate = canUpdate
            this.createdAt = LocalDateTime.now()
        }

        `when`(userRepository.findById(authorId)).thenReturn(Optional.of(simpleUser))
        `when`(todoRepository.save(any())).thenReturn(simpleTodo)

        // when
        val todoVO = todoService.create(authorId, content, reason, state, canUpdate)

        // then
        verify(todoRepository).save(any())
        assertThat(todoVO).isNotNull
        assertThat(todoVO?.content).isEqualTo(content)
        assertThat(todoVO?.authorId).isEqualTo(authorId)
        assertThat(todoVO?.state).isEqualTo(state)
    }

    @Test
    fun create_유저를_찾을수없음() {
        // given
        val content = generateSimpleString()
        val authorId = generateSimpleAbsoluteLong()
        val state = State.NONE
        val reason = generateSimpleString()
        val canUpdate = true

        `when`(userRepository.findById(authorId)).thenReturn(Optional.empty())

        // then
        assertThrows<UserNotFoundException> {
            todoService.create(authorId, content, reason, state, canUpdate)
        }
    }

    @Test
    fun update_성공() {
        // given
        val todoId = generateSimpleAbsoluteLong()
        val content = generateSimpleString()
        val authorId = generateSimpleAbsoluteLong()
        val simpleUser = User().apply { id = authorId }
        val state = State.NONE
        val reason = generateSimpleString()
        val canUpdate = true
        val simpleTodo = Todo().apply {
            this.id = todoId
            this.content = content
            this.author = simpleUser
            this.state = state
            this.reason = reason
            this.canUpdate = canUpdate
            this.createdAt = LocalDateTime.now()
        }

        `when`(todoRepository.findById(todoId)).thenReturn(Optional.of(simpleTodo))

        // when
        todoService.update(todoId, authorId, content, reason, state, canUpdate)
    }

    @Test
    fun update_Todo를_찾을수없음() {
        // given
        val todoId = generateSimpleAbsoluteLong()
        val content = generateSimpleString()
        val authorId = generateSimpleAbsoluteLong()
        val state = State.NONE
        val reason = generateSimpleString()
        val canUpdate = true

        `when`(todoRepository.findById(todoId)).thenReturn(Optional.empty())

        // then
        assertThrows<TodoNotFoundException> {
            todoService.update(todoId, authorId, content, reason, state, canUpdate)
        }
    }

    @Test
    fun update_Todo에_접근할수없음() {
        // given
        val todoId = generateSimpleAbsoluteLong()
        val content = generateSimpleString()
        val authorId = generateSimpleAbsoluteLong()
        val differentAuthorId = generateSimpleAbsoluteLong()
        val simpleUser = User().apply { id = authorId }
        val state = State.NONE
        val reason = generateSimpleString()
        val canUpdate = true
        val simpleTodo = Todo().apply {
            this.id = todoId
            this.content = content
            this.author = simpleUser
            this.state = state
            this.reason = reason
            this.canUpdate = canUpdate
            this.createdAt = LocalDateTime.now()
        }

        `when`(todoRepository.findById(todoId)).thenReturn(Optional.of(simpleTodo))

        // then
        assertThrows<TodoAccessDeniedException> {
            todoService.update(todoId, differentAuthorId, content, reason, state, canUpdate)
        }
    }
}
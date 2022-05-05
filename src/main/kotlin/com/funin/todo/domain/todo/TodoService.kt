package com.funin.todo.domain.todo

import com.funin.todo.domain.exception.TodoAccessDeniedException
import com.funin.todo.domain.exception.TodoNotFoundException
import com.funin.todo.domain.exception.UserNotFoundException
import com.funin.todo.domain.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface TodoService {
    fun create(
        userId: Long,
        content: String = "",
        reason: String? = null,
        state: State = State.NONE,
        canUpdate: Boolean = true
    ): TodoVO?

    fun update(
        todoId: Long,
        userId: Long,
        content: String = "",
        reason: String? = null,
        state: State = State.NONE,
        canUpdate: Boolean = true
    )
}

@Service
@Transactional(readOnly = true)
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val userRepository: UserRepository
) : TodoService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Transactional
    override fun create(userId: Long, content: String, reason: String?, state: State, canUpdate: Boolean): TodoVO? {
        val findUser = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException(userId)
        val todo = Todo().apply {
            this.author = findUser
            this.content = content
            this.reason = reason
            this.state = state
            this.canUpdate = canUpdate
        }
        return todoRepository.save(todo).toTodoVO()
    }

    @Transactional
    override fun update(
        todoId: Long,
        userId: Long,
        content: String,
        reason: String?,
        state: State,
        canUpdate: Boolean
    ) {
        val findTodo = todoRepository.findByIdOrNull(todoId) ?: throw TodoNotFoundException(todoId)
        if (findTodo.author?.id != userId) throw TodoAccessDeniedException()
        findTodo.apply {
            this.content = content
            this.reason = reason
            this.state = state
            this.canUpdate = canUpdate
        }
    }
}
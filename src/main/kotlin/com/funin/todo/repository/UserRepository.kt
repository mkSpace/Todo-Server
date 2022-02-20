package com.funin.todo.repository

import com.funin.todo.entity.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Repository
@Transactional(readOnly = true)
class UserRepository(val entityManager: EntityManager) {

    @Transactional
    fun save(username: String): User {
        val user = User()
        user.nickname = "funin"
        entityManager.persist(user)
        return user
    }
}
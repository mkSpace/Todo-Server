package com.funin.todo.domain.user

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class UserRepository(private val entityManager: EntityManager) {

    fun save(username: String): User {
        val user = User()
        user.nickname = "funin"
        entityManager.persist(user)
        return user
    }
}
package com.funin.todo.domain.user

import com.funin.todo.presentation.utils.CipherManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
internal class UserTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var cipherManager: CipherManager

    @Test
    @Transactional
    fun save() {
        val user = User()
        user.nickname = "funin-todo"
        val salt = cipherManager.generateSalt()
        val password = "test-password"
        user.password = cipherManager.encodeSHA256(password, salt)
        user.salt = salt
        entityManager.persist(user)

        val findUser = entityManager
            .createQuery(
                "select user from User user where user.nickname=:username",
                User::class.java
            )
            .setParameter("username", "funin-todo")
            .singleResult

        Assertions.assertThat(user).isEqualTo(findUser)
    }

}
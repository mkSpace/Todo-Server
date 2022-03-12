package com.funin.todo.domain.user

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

    @Transactional
    @Test
    fun test() {
        val user = User()
        user.nickname = "Jaemin"
        entityManager.persist(user)

        val findUser = entityManager.createQuery("select user from User user where user.nickname=:username", User::class.java)
            .setParameter("username", "Jaemin")
            .singleResult

        Assertions.assertThat(user).isEqualTo(findUser)
    }

}
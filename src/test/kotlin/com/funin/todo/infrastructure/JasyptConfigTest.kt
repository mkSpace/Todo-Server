package com.funin.todo.infrastructure

import com.funin.todo.ActiveTestProfile
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.ConfigurableEnvironment
import java.util.*

@Disabled("필요할 때만 사용합니다")
@ActiveTestProfile
@SpringBootTest
class JasyptConfigTest {

    @Value("\${jasypt.encryptor.password}")
    lateinit var jasyptEncryptorPassword: String

    @Autowired
    lateinit var configurableEnvironment: ConfigurableEnvironment

    lateinit var encryptor: DefaultLazyEncryptor

    @BeforeEach
    fun setUp() {
        check(jasyptEncryptorPassword.isNotBlank()) {
            "jasypt.encryptor.password must not be null, empty or blank"
        }
        encryptor = DefaultLazyEncryptor(configurableEnvironment)
    }

    @Test
    fun testForEncryption() {
        val source = UUID.randomUUID().toString()
        println("source: $source")
        println("encrypted: ${encryptor.encrypt(source)}")
    }

    @Test
    fun testForDecryption() {
        val source = "9qPSlnnVXsfEyBJYE237aPFWttgLZ9xxq/xWAN7iPfoSYBNCPGHGMgaeMg/kXe5g"
        println("source: $source")
        println("decrpyted: ${encryptor.decrypt(source)}")
    }
}
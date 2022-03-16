package com.funin.todo.presentation.utils

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
internal class CipherManagerTest {

    @Autowired
    lateinit var cipherManager: CipherManager

    @Test
    @DisplayName("generate 된 salt 는 서로 달라야 합니다.")
    fun generate_salt() {
        val salt1 = cipherManager.generateSalt()
        val salt2 = cipherManager.generateSalt()
        val salt3 = cipherManager.generateSalt()

        Assertions.assertThat(salt1).isNotEqualTo(salt2)
        Assertions.assertThat(salt1).isNotEqualTo(salt3)
        Assertions.assertThat(salt2).isNotEqualTo(salt3)
    }

    @Test
    @DisplayName("같은 salt에 같은 password인 경우 결과 hash가 같아야 합니다.")
    fun encode_SHA_256_same_salt_with_password() {
        val salt = cipherManager.generateSalt()
        val password1 = UUID.randomUUID().toString()
        val password2 = UUID.randomUUID().toString()
        val encodedPassword1 = cipherManager.encodeSHA256(password1, salt)
        val encodedPassword2 = cipherManager.encodeSHA256(password1, salt)
        val encodedPassword3 = cipherManager.encodeSHA256(password2, salt)

        Assertions.assertThat(encodedPassword1).isEqualTo(encodedPassword2)
        Assertions.assertThat(encodedPassword1).isNotEqualTo(encodedPassword3)
    }

    @Test
    @DisplayName("같은 password에 salt가 둘 다 없는 경우 hash가 같아야 합니다.")
    fun encode_SHA_256_no_salt_with_password() {
        val password1 = UUID.randomUUID().toString()
        val password2 = UUID.randomUUID().toString()
        val encodedPassword1 = cipherManager.encodeSHA256(password1)
        val encodedPassword2 = cipherManager.encodeSHA256(password1)
        val encodedPassword3 = cipherManager.encodeSHA256(password2)

        Assertions.assertThat(encodedPassword1).isEqualTo(encodedPassword2)
        Assertions.assertThat(encodedPassword1).isNotEqualTo(encodedPassword3)
    }

    @Test
    @DisplayName("같은 password의 경우 decode, encode의 결과가 원문과 같아야 합니다.")
    fun encode_base_64_is_equal() {
        val password = UUID.randomUUID().toString()
        val encoded = cipherManager.encodeBase64(password.toByteArray())
        val decoded = cipherManager.decodeBase64(encoded)

        Assertions.assertThat(String(decoded)).isEqualTo(password)
    }

    @Test
    @DisplayName("다른 password의 경우 decode, encode 비교 시 같지 않아야 합니다.")
    fun encode_base_64_is_not_equal() {
        val password1 = UUID.randomUUID().toString()
        val password2 = UUID.randomUUID().toString()
        val encoded1 = cipherManager.encodeBase64(password1.toByteArray())
        val encoded2 = cipherManager.encodeBase64(password2.toByteArray())

        Assertions.assertThat(encoded1).isNotEqualTo(encoded2)
    }
}
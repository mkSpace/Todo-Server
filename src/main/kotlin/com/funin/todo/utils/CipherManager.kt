package com.funin.todo.utils

import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

@Component
class CipherManager {

    // String 은 기본적으로 immutable 하기 때문에 heap에 encode 된 password가 남아 있을 수 있어 새로운 인스턴스를 생성합니다.
    fun encodeSHA256(input: String, salt: ByteArray? = null): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        salt?.let { messageDigest.update(it) }
        return String(messageDigest.digest(input.toByteArray()))
    }

    fun generateSalt(): ByteArray {
        val bytes = ByteArray(16)
        SecureRandom.getInstance("SHA1PRNG").nextBytes(bytes)
        return encodeBase64(bytes)
    }

    fun encodeBase64(bytes: ByteArray): ByteArray = Base64.getEncoder().encode(bytes)

    fun decodeBase64(encoded: ByteArray): ByteArray = Base64.getDecoder().decode(encoded)
}
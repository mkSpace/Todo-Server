package com.funin.todo.domain.user

import com.funin.todo.domain.exception.UserDuplicatedException
import com.funin.todo.domain.exception.UserNotFoundException
import com.funin.todo.presentation.utils.CipherManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserService {
    fun join(email: String, nickname: String, password: String): UserVO?
    fun findById(userId: Long): User?
    fun login(email: String, password: String): UserVO?
}

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val cipherManager: CipherManager
) : UserService {

    @Transactional
    override fun join(email: String, nickname: String, password: String): UserVO? {
        val findMember = userRepository.findByEmail(email) ?: userRepository.findByNickname(nickname)
        if (findMember != null) {
            throw UserDuplicatedException(nickname)
        }
        val salt = cipherManager.generateSalt()
        val user = User().apply {
            this.email = email
            this.nickname = nickname
            this.password = cipherManager.encodeSHA256(password, salt)
            this.salt = salt
        }
        return userRepository.save(user).toUserVO()
    }

    override fun login(email: String, password: String): UserVO? {
        val findMember = userRepository.findByEmail(email) ?: throw UserNotFoundException("이메일에 해당하는 유저가 없습니다.")
        val salt = findMember.salt
        val decoded = cipherManager.encodeSHA256(password, salt)
        return if (decoded == findMember.password) {
            findMember.toUserVO()
        } else {
            null
        }
    }

    override fun findById(userId: Long): User? {
        return userRepository.findByIdOrNull(userId)
    }
}
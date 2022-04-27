package com.funin.todo.domain.user

import com.funin.todo.domain.exception.UserEmailDuplicatedException
import com.funin.todo.domain.exception.UserNicknameDuplicatedException
import com.funin.todo.domain.exception.UserNotFoundException
import com.funin.todo.presentation.utils.CipherManager
import org.slf4j.LoggerFactory
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

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Transactional
    override fun join(email: String, nickname: String, password: String): UserVO? {
        val findMemberByEmail = userRepository.findByEmail(email)
        val findMemberByNickname = userRepository.findByNickname(nickname)
        if (findMemberByEmail != null) {
            throw UserEmailDuplicatedException(email)
        }
        if (findMemberByNickname != null) {
            throw UserNicknameDuplicatedException(nickname)
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
        if (decoded != findMember.password) {
            log.error("비밀번호가 일치하지 않음. decoded: $decoded, salt: $salt")
            throw UserNotFoundException("비밀번호가 일치하지 않습니다.")
        }
        return findMember.toUserVO()
    }

    override fun findById(userId: Long): User? {
        return userRepository.findByIdOrNull(userId)
    }
}
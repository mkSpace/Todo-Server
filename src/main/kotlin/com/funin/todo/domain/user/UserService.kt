package com.funin.todo.domain.user

import com.funin.todo.domain.exception.UserDuplicatedException
import com.funin.todo.presentation.utils.CipherManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserService {
    fun join(nickname: String, password: String): UserVO
}

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val cipherManager: CipherManager
) : UserService {

    @Transactional
    override fun join(nickname: String, password: String): UserVO {
        val findMember = userRepository.findByNickname(nickname)
        if (findMember != null) {
            throw UserDuplicatedException(nickname)
        }
        val salt = cipherManager.generateSalt()
        val user = User().apply {
            this.nickname = nickname
            this.password = cipherManager.encodeSHA256(password, salt)
            this.salt = salt
        }
        userRepository.save(user)
        return user.toUserVO()
    }
}
package com.funin.todo.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserService {
    fun join(nickname: String, password: String): UserVO
}

@Service
@Transactional(readOnly = true)
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    @Transactional
    override fun join(nickname: String, password: String): UserVO {
        val findMember = userRepository.findByNickname(nickname)
        if (findMember != null) {
            return findMember.toUserVO()
        }
        val user = User().apply {
            this.nickname = nickname
            this.password = password
        }
        userRepository.save(user)
        return user.toUserVO()
    }
}
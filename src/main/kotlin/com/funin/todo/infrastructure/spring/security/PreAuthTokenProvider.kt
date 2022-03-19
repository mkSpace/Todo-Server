package com.funin.todo.infrastructure.spring.security

import com.funin.todo.application.TokenService
import com.funin.todo.domain.exception.UserNotFoundException
import com.funin.todo.domain.user.UserService
import com.funin.todo.infrastructure.Role
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class PreAuthTokenProvider : AuthenticationProvider {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtService: TokenService<Long>

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication?): Authentication {
        log.debug("authentication: {}", SecurityContextHolder.getContext().authentication)
        if (authentication is PreAuthenticatedAuthenticationToken) {
            val token = authentication.principal as? String
            val user = jwtService.decode(token)
                ?.let { userService.findById(it) }
                ?: throw UserNotFoundException("회원 정보가 없습니다. token: $token")
            return UsernamePasswordAuthenticationToken(
                user.id.toString(),
                "",
                listOf(SimpleGrantedAuthority(Role.MEMBER))
            )
        }
        throw TokenMissingException("Invalid token")
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return PreAuthenticatedAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
package com.funin.todo.infrastructure.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.funin.todo.application.TokenService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtService(
    @Value("\${jwt.token-issuer}")
    private val tokenIssuer: String,
    @Value("\${jwt.token-signing-key}")
    private val tokenSigningKey: String
) : TokenService<Long> {

    companion object {
        private const val CLAIM_NAME_USER_ID = "userId"
    }

    private val algorithm: Algorithm = Algorithm.HMAC256(tokenSigningKey)
    private val jwtVerifier: JWTVerifier = JWT.require(algorithm).build()

    override fun encode(userId: Long): String = JWT.create()
        .withIssuer(tokenIssuer)
        .withClaim(CLAIM_NAME_USER_ID, userId)
        .sign(algorithm)

    override fun decode(token: String?): Long? {
        return try {
            jwtVerifier.verify(token).claims[CLAIM_NAME_USER_ID]?.asLong()
        } catch (e: JWTVerificationException) {
            null
        }
    }
}
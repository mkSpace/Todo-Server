package com.funin.todo.infrastructure.spring.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.funin.todo.domain.ResultCode
import com.funin.todo.infrastructure.Role
import com.funin.todo.presentation.api.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter

@EnableWebSecurity
@ConditionalOnWebApplication
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(SecurityConfig::class.java)
    }

    @Autowired
    lateinit var objectMapper: ObjectMapper

    override fun configure(web: WebSecurity?) {
        web?.ignoring()
            ?.mvcMatchers(
                "/error"
            )
    }

    override fun configure(http: HttpSecurity?) {
        http ?: return
        http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/api/v1/signup").permitAll()
            .antMatchers("/api/v1/login").permitAll()
            .antMatchers("/api/**").hasAuthority(Role.MEMBER)
        http.csrf().disable()
        http.logout().disable()
        http.formLogin().disable()
        http.httpBasic().disable()
        http.requestCache().disable()
        http.addFilterAt(tokenPreAuthFilter(), AbstractPreAuthenticatedProcessingFilter::class.java)
        http.sessionManagement().disable()
        http.cors().disable()
        http.exceptionHandling()
            .authenticationEntryPoint { request, response, authException ->
                log.debug("authenticationEntryPoint: {}, {}, {}", request, response, authException)
                response.status = HttpStatus.UNAUTHORIZED.value()
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                objectMapper.writeValue(
                    response.outputStream,
                    ApiResponse.failure<Unit>(ResultCode.UNAUTHORIZED)
                )
            }
            .accessDeniedHandler{ request, response, accessDeniedException ->
                log.debug("accessDeniedHandler: {}, {}, {}", request, response, accessDeniedException)
                response.status = HttpStatus.FORBIDDEN.value()
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                objectMapper.writeValue(
                    response.outputStream,
                    ApiResponse.failure<Unit>(ResultCode.FORBIDDEN)
                )
            }
    }

    @Bean
    fun tokenPreAuthFilter(): TokenPreAuthFilter {
        return TokenPreAuthFilter().apply { setAuthenticationManager(ProviderManager(preAuthTokenProvider())) }
    }

    @Bean
    fun preAuthTokenProvider(): PreAuthTokenProvider = PreAuthTokenProvider()
}
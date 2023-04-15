package com.github.bkhablenko.upday.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

// Implement WebMvcConfigurer so @WebMvcTest includes this configuration
@Configuration
@EnableMethodSecurity
class WebSecurityConfig : WebMvcConfigurer {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity {
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
            httpBasic {}
        }
        return httpSecurity.build()
    }
}

package com.github.bkhablenko.upday.config

import com.github.bkhablenko.upday.web.interceptor.RequestIdHandlerInterceptor
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ConditionalOnWebApplication
class WebConfig : WebMvcConfigurer {

    private val interceptors = listOf<HandlerInterceptor>(
        RequestIdHandlerInterceptor(),
    )

    override fun addInterceptors(registry: InterceptorRegistry) {
        interceptors.forEach {
            registry.addInterceptor(it)
        }
    }
}

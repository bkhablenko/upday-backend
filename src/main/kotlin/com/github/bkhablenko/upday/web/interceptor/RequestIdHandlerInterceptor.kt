package com.github.bkhablenko.upday.web.interceptor

import io.opentelemetry.api.trace.Span
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class RequestIdHandlerInterceptor : HandlerInterceptor {

    companion object {
        private const val HEADER_NAME = "X-Request-Id"
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        exception: Exception?,
    ) {
        val traceId = Span.current().spanContext.traceId
        response.setHeader(HEADER_NAME, traceId)
    }
}

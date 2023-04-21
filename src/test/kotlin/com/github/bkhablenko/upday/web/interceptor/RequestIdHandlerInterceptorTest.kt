package com.github.bkhablenko.upday.web.interceptor

import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.TraceFlags
import io.opentelemetry.api.trace.TraceState
import io.opentelemetry.context.Context
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class RequestIdHandlerInterceptorTest {

    companion object {
        private const val TRACE_ID_HEX = "b65f56d53190ab396d50aa0843b4d3ee"
    }

    private val handlerInterceptor = RequestIdHandlerInterceptor()

    @BeforeEach
    fun setUp() {
        val traceFlags = TraceFlags.getDefault()
        val traceState = TraceState.getDefault()

        makeCurrent(SpanContext.create(TRACE_ID_HEX, "0c0b4510d48d8d22", traceFlags, traceState))
    }

    @Test
    fun testAfterCompletion() {
        val response = MockHttpServletResponse()
        handlerInterceptor.afterCompletion(MockHttpServletRequest(), response, Unit, null)

        val requestId = response.getHeaderValue("X-Request-Id")
        assertThat(requestId, equalTo(TRACE_ID_HEX))
    }

    private fun makeCurrent(spanContext: SpanContext) {
        Context.current().with(Span.wrap(spanContext)).makeCurrent()
    }
}

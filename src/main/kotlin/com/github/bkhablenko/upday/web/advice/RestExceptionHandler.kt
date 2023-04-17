package com.github.bkhablenko.upday.web.advice

import com.github.bkhablenko.upday.exception.NotFoundException
import com.github.bkhablenko.upday.web.model.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(request: HttpServletRequest, cause: NotFoundException): ResponseEntity<ErrorResponse> {
        return errorResponseOf(request, HttpStatus.NOT_FOUND, cause)
    }

    private fun errorResponseOf(
        request: HttpServletRequest,
        httpStatus: HttpStatus,
        cause: Exception,
    ): ResponseEntity<ErrorResponse> {
        val payload = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = httpStatus.value(),
            error = httpStatus.reasonPhrase,
            message = cause.message ?: "",
            path = request.requestURI,
        )
        return ResponseEntity(payload, httpStatus)
    }
}

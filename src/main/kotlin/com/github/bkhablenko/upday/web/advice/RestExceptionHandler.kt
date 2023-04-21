package com.github.bkhablenko.upday.web.advice

import com.github.bkhablenko.upday.exception.NotFoundException
import com.github.bkhablenko.upday.web.model.Id
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler : AbstractRestExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(request: HttpServletRequest, exception: NotFoundException): ErrorResponseEntity {
        val encodedResourceId = Id.encode(exception.resourceId)
        return errorResponseOf(request, NOT_FOUND, "Resource $encodedResourceId does not exist")
    }
}

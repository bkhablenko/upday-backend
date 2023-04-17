package com.github.bkhablenko.upday.web.controller

import com.github.bkhablenko.upday.service.AuthorService
import com.github.bkhablenko.upday.web.model.GetAuthorByIdResponse
import com.github.bkhablenko.upday.web.model.GetAuthorsResponse
import com.github.bkhablenko.upday.web.model.Id
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/authors")
class AuthorController(private val authorService: AuthorService) {

    @GetMapping
    fun getAuthors(): GetAuthorsResponse {
        val authors = authorService.getAuthors()

        return GetAuthorsResponse.of(authors)
    }

    @GetMapping("/{authorId}")
    fun getAuthorById(@PathVariable authorId: Id): GetAuthorByIdResponse {
        val author = authorService.getAuthorById(authorId.value)

        return GetAuthorByIdResponse.of(author)
    }
}

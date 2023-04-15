package com.github.bkhablenko.upday.web.controller

import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.service.AuthorService
import com.github.bkhablenko.upday.web.model.AuthorItem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/authors")
class AuthorController(private val authorService: AuthorService) {

    @GetMapping
    fun getAuthors(): List<AuthorItem> {
        return authorService.getAuthors().map { it.toAuthorItem() }
    }

    @GetMapping("/{authorId}")
    fun getAuthorById(@PathVariable authorId: UUID): AuthorItem {
        return authorService.getAuthorById(authorId).toAuthorItem()
    }

    private fun AuthorEntity.toAuthorItem() = AuthorItem(id, fullName)
}

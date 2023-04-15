package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.AuthorEntity
import java.util.UUID

interface AuthorService {

    fun getAuthors(): List<AuthorEntity>

    fun getAuthorById(authorId: UUID): AuthorEntity
}

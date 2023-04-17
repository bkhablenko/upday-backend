package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.domain.repository.AuthorRepository
import com.github.bkhablenko.upday.exception.AuthorNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DefaultAuthorService(private val authorRepository: AuthorRepository) : AuthorService {

    override fun getAuthors(): List<AuthorEntity> {
        return authorRepository.findAll()
    }

    override fun getAuthorById(authorId: UUID): AuthorEntity {
        return authorRepository.findByIdOrNull(authorId) ?: throw AuthorNotFoundException(authorId)
    }
}

package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.domain.repository.AuthorRepository
import com.github.bkhablenko.upday.exception.AuthorNotFoundException
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Optional
import java.util.UUID.randomUUID

@DisplayName("DefaultAuthorService")
class DefaultAuthorServiceTest {

    companion object {
        private const val FULL_NAME = "Hunter S. Thompson"
    }

    private val authorRepository = mock<AuthorRepository>()

    private val authorService = DefaultAuthorService(authorRepository)

    @DisplayName("getAuthors")
    @Nested
    inner class GetAuthorsTest {

        @Test
        fun `should return all author entities`() {
            // Given
            val expectedEntity = AuthorEntity(FULL_NAME)
            whenever(authorRepository.findAll()) doReturn listOf(expectedEntity)

            // When
            val authors = authorService.getAuthors()

            // Then
            assertThat(authors, hasSize(1))
            assertThat(authors[0], sameInstance(expectedEntity))
        }
    }

    @DisplayName("getAuthorById")
    @Nested
    inner class GetAuthorByIdTest {

        @Test
        fun `should return author entity if found`() {
            // Given
            val authorId = randomUUID()
            val expectedEntity = AuthorEntity(FULL_NAME).apply { id = authorId }
            whenever(authorRepository.findById(authorId)) doReturn Optional.of(expectedEntity)

            // When
            val author = authorService.getAuthorById(authorId)

            // Then
            assertThat(author, sameInstance(expectedEntity))
        }

        @Test
        fun `should throw exception if author entity not found`() {
            // Given
            val authorId = randomUUID()
            whenever(authorRepository.findById(authorId)) doReturn Optional.empty()

            // Expect
            assertThrows<AuthorNotFoundException> { authorService.getAuthorById(authorId) }
        }
    }
}

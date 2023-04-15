package com.github.bkhablenko.upday.web.controller

import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.exception.AuthorNotFoundException
import com.github.bkhablenko.upday.service.AuthorService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.UUID
import java.util.UUID.randomUUID

@WebMvcTest(AuthorController::class)
class AuthorControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authorService: AuthorService

    @DisplayName("GET /api/v1/authors")
    @Nested
    inner class GetAuthorsTest {

        @Test
        fun `should respond with 200 OK on success`() {
            // Given
            val author = AuthorEntity("Hunter S. Thompson").apply { id = randomUUID() }
            whenever(authorService.getAuthors()) doReturn listOf(author)

            // Expect
            getAuthors().andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$") { isArray() }
                jsonPath("$.length()") { value(1) }
                with(author) {
                    jsonPath("$[0].id") { value(id.toString()) }
                    jsonPath("$[0].fullName") { value(fullName) }
                }
            }
        }

        private fun getAuthors() = mockMvc.get("/api/v1/authors").andDo { print() }
    }

    @DisplayName("GET /api/v1/authors/{authorId}")
    @Nested
    inner class GetAuthorByIdTest {

        @Test
        fun `should respond with 200 OK on success`() {
            // Given
            val authorId = randomUUID()
            val author = AuthorEntity("Hunter S. Thompson").apply { id = authorId }
            whenever(authorService.getAuthorById(authorId)) doReturn author

            // Expect
            getAuthorById(authorId).andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                with(author) {
                    jsonPath("$.id") { value(id.toString()) }
                    jsonPath("$.fullName") { value(fullName) }
                }
            }
        }

        @Test
        fun `should respond with 404 Not Found if author does not exist`() {
            // Given
            val authorId = randomUUID()
            doThrow(AuthorNotFoundException(authorId)).whenever(authorService).getAuthorById(authorId)

            // Expect
            getAuthorById(authorId).andExpect {
                status { isNotFound() }
            }
        }

        private fun getAuthorById(authorId: UUID) = mockMvc.get("/api/v1/authors/$authorId").andDo { print() }
    }
}

package com.github.bkhablenko.upday.web.controller

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.exception.ArticleNotFoundException
import com.github.bkhablenko.upday.service.ArticleService
import com.github.bkhablenko.upday.web.model.Id
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.time.LocalDate
import java.time.Month.APRIL
import java.time.Month.MAY
import java.util.UUID
import java.util.UUID.randomUUID

@WebMvcTest(ArticleController::class)
class ArticleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var articleService: ArticleService

    @DisplayName("POST /api/v1/articles")
    @Nested
    inner class PublishArticleTest {

        // TODO(bkhablenko): Add missing tests

        @Test
        fun `should respond with 401 Unauthorized on missing Authorization`() {
            val minimalJson = """{"title":"","description":"","body":"","tags":[],"authors":[]}"""
            publishArticle(minimalJson).andExpect { status { isUnauthorized() } }

            verify(articleService, never()).createArticle(any())
        }

        @Test
        @WithMockUser(roles = ["USER"])
        fun `should respond with 403 Forbidden on insufficient access`() {
            val minimalJson = """{"title":"","description":"","body":"","tags":[],"authors":[]}"""
            publishArticle(minimalJson).andExpect { status { isForbidden() } }

            verify(articleService, never()).createArticle(any())
        }

        private fun publishArticle(content: String) =
            mockMvc
                .post("/api/v1/articles") {
                    this.content = content
                    this.contentType = MediaType.APPLICATION_JSON
                }
                .andDo { print() }
    }

    @DisplayName("PUT /api/v1/articles/{articleId}")
    @Nested
    inner class EditArticleTest {

        // TODO(bkhablenko): Add missing tests

        @Test
        fun `should respond with 401 Unauthorized on missing Authorization`() {
            val articleId = Id.random()
            editArticle(articleId, "{}").andExpect { status { isUnauthorized() } }

            verify(articleService, never()).createArticle(any())
        }

        @Test
        @WithMockUser(roles = ["USER"])
        fun `should respond with 403 Forbidden on insufficient access`() {
            val articleId = Id.random()
            editArticle(articleId, "{}").andExpect { status { isForbidden() } }

            verify(articleService, never()).updateArticle(any(), any())
        }

        private fun editArticle(articleId: Id, content: String) =
            mockMvc
                .put("/api/v1/articles/$articleId") {
                    this.content = content
                    this.contentType = MediaType.APPLICATION_JSON
                }
                .andDo { print() }
    }

    @DisplayName("DELETE /api/v1/articles")
    @Nested
    inner class RemoveArticleTest {

        @Test
        @WithMockUser(roles = ["EDITOR"])
        fun `should respond with 204 No Content on success`() {
            val articleId = randomUUID()
            doNothing().whenever(articleService).deleteArticleById(articleId)

            removeArticle(articleId).andExpect {
                status { isNoContent() }
            }
        }

        @Test
        fun `should respond with 401 Unauthorized on missing Authorization`() {
            val articleId = randomUUID()

            removeArticle(articleId).andExpect {
                status { isUnauthorized() }
            }
            verify(articleService, never()).deleteArticleById(articleId)
        }

        @Test
        @WithMockUser(roles = ["USER"])
        fun `should respond with 403 Forbidden on insufficient access`() {
            val articleId = randomUUID()

            removeArticle(articleId).andExpect {
                status { isForbidden() }
            }
            verify(articleService, never()).deleteArticleById(articleId)
        }

        @Test
        @WithMockUser(roles = ["EDITOR"])
        fun `should respond with 404 Not Found if article does not exist`() {
            val articleId = randomUUID()
            whenever(articleService.deleteArticleById(articleId)) doThrow ArticleNotFoundException(articleId)

            removeArticle(articleId).andExpect {
                status { isNotFound() }
            }
        }

        private fun removeArticle(articleId: UUID) = removeArticle(Id.encode(articleId))

        private fun removeArticle(articleId: Id) = mockMvc.delete("/api/v1/articles/$articleId").andDo { print() }
    }

    @DisplayName("GET /api/v1/articles/{articleId}")
    @Nested
    inner class GetArticleByIdTest {

        @Test
        fun `should respond with 200 OK on success`() {
            val articleId = randomUUID()

            val author = AuthorEntity(
                fullName = "Hunter S. Thompson",
            ).apply {
                id = randomUUID()
            }
            val article = ArticleEntity(
                title = "Song of the Sausage Creature",
                description = "A wild ride on a Ducati, celebrating the thrill and culture of motorcycling.",
                body = "Some fancy text goes here.",
                tags = listOf("motorcycles"),
                authors = listOf(author),
            ).apply {
                id = articleId
                LocalDate.of(2023, APRIL, 15).atStartOfDay().let {
                    createdDate = it
                    lastModifiedDate = it
                }
            }
            whenever(articleService.getArticleById(articleId)) doReturn article

            getArticleById(articleId).andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }

                with(article) {
                    jsonPath("$.id", equalTo(Id.encode(id).base58Encoded))
                    jsonPath("$.title", equalTo(title))
                    jsonPath("$.description", equalTo(description))
                    jsonPath("$.body", equalTo(body))
                    jsonPath("$.tags", containsInAnyOrder(*tags.toTypedArray()))
                    jsonPath("$.publicationDate", equalTo("2023-04-15"))
                }
                jsonPath("$.authors") { isArray() }
                jsonPath("$.authors.length()", equalTo(1))
                with(author) {
                    jsonPath("$.authors[0].id", equalTo(Id.encode(id).base58Encoded))
                    jsonPath("$.authors[0].fullName", equalTo(fullName))
                }
            }
        }

        @Test
        fun `should respond with 404 Not Found if article does not exist`() {
            val articleId = randomUUID()
            whenever(articleService.getArticleById(articleId)) doThrow ArticleNotFoundException(articleId)

            getArticleById(articleId).andExpect {
                status { isNotFound() }
            }
        }

        private fun getArticleById(articleId: UUID) = getArticleById(Id.encode(articleId))

        private fun getArticleById(articleId: Id) = mockMvc.get("/api/v1/articles/$articleId").andDo { print() }
    }

    @DisplayName("GET /api/v1/articles")
    @Nested
    inner class SearchArticlesTest {

        @Test
        fun `should respond with 200 OK on success`() {
            val articleId = Id.random()

            val authorId = Id.random()
            val tags = listOf("motorcycles")
            val publicationDateStart = LocalDate.of(2023, APRIL, 1)
            val publicationDateEndExclusive = LocalDate.of(2023, MAY, 1)

            val author = AuthorEntity(
                fullName = "Hunter S. Thompson",
            ).apply {
                id = authorId.value
            }
            val article = ArticleEntity(
                title = "Song of the Sausage Creature",
                description = "A wild ride on a Ducati, celebrating the thrill and culture of motorcycling.",
                body = "Some fancy text goes here.",
                tags = tags,
                authors = listOf(author),
            ).apply {
                id = articleId.value
                LocalDate.of(2023, APRIL, 15).atStartOfDay().let {
                    createdDate = it
                    lastModifiedDate = it
                }
            }
            whenever(articleService.searchArticles(any())) doReturn listOf(article)

            searchArticles(authorId, tags, publicationDateStart, publicationDateEndExclusive).andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }

                jsonPath("$.articles") { isArray() }
                jsonPath("$.articles.length()", equalTo(1))
                with(article) {
                    jsonPath("$.articles[0].id", equalTo(articleId.base58Encoded))
                    jsonPath("$.articles[0].title", equalTo(title))
                    jsonPath("$.articles[0].description", equalTo(description))
                    jsonPath("$.articles[0].body", equalTo(body))
                    jsonPath("$.articles[0].tags", containsInAnyOrder(*tags.toTypedArray()))
                    jsonPath("$.articles[0].publicationDate", equalTo("2023-04-15"))
                }
                jsonPath("$.articles[0].authors") { isArray() }
                jsonPath("$.articles[0].authors.length()", equalTo(1))
                with(author) {
                    jsonPath("$.articles[0].authors[0].id", equalTo(authorId.base58Encoded))
                    jsonPath("$.articles[0].authors[0].fullName", equalTo(fullName))
                }
            }
        }

        private fun searchArticles(
            authorId: Id? = null,
            tags: List<String>? = null,
            publicationDateStart: LocalDate? = null,
            publicationDateEndExclusive: LocalDate? = null,
        ) = mockMvc
            .get("/api/v1/articles") {
                authorId?.let { param("authorId", authorId) }
                tags?.let { param("tags", tags) }
                publicationDateStart?.let { param("publicationDateStart", publicationDateStart) }
                publicationDateEndExclusive?.let { param("publicationDateEndExclusive", publicationDateEndExclusive) }
            }
            .andDo { print() }

        private fun MockHttpServletRequestDsl.param(name: String, value: Any) {
            val stringValue = when (value) {
                is Iterable<*> -> value.joinToString(",")
                else -> value.toString()
            }
            param(name, stringValue)
        }
    }
}

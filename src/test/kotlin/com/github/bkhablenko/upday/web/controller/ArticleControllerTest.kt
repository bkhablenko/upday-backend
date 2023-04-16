package com.github.bkhablenko.upday.web.controller

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.service.ArticleService
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
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

    @DisplayName("GET /api/v1/articles")
    @Nested
    inner class SearchArticlesTest {

        @Test
        fun `should respond with 200 OK on success`() {
            val authorId = randomUUID()
            val tags = listOf("health", "politics")
            val publicationDateStart = LocalDate.of(2023, APRIL, 1)
            val publicationDateEndExclusive = LocalDate.of(2023, MAY, 1)

            val author = AuthorEntity(
                fullName = "Hunter S. Thompson",
            ).apply {
                id = authorId
            }
            val article = ArticleEntity(
                title = "Song of the Sausage Creature",
                description = "A wild ride on a Ducati, celebrating the thrill and culture of motorcycling.",
                body = "Just some non-empty string.",
                tags = tags,
                authors = listOf(author),
            ).apply {
                id = randomUUID()
                LocalDate.of(2023, APRIL, 15).atStartOfDay().let {
                    createdDate = it
                    lastModifiedDate = it
                }
            }
            whenever(articleService.searchArticles(any())) doReturn listOf(article)

            searchArticles(authorId, tags, publicationDateStart, publicationDateEndExclusive).andExpect {
                status { isOk() }
                content { MediaType.APPLICATION_JSON }

                jsonPath("$") { isArray() }
                jsonPath("$.length()", equalTo(1))
                with(article) {
                    jsonPath("$[0].id", equalTo(id.toString()))
                    jsonPath("$[0].title", equalTo(title))
                    jsonPath("$[0].description", equalTo(description))
                    jsonPath("$[0].body", equalTo(body))
                    jsonPath("$[0].tags", containsInAnyOrder(*tags.toTypedArray()))
                    jsonPath("$[0].publicationDate", equalTo("2023-04-15"))
                }
                jsonPath("$[0].authors") { isArray() }
                jsonPath("$[0].authors.length()", equalTo(1))
                with(author) {
                    jsonPath("$[0].authors[0].id", equalTo(id.toString()))
                    jsonPath("$[0].authors[0].fullName", equalTo(fullName))
                }
            }
        }

        private fun searchArticles(
            authorId: UUID? = null,
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

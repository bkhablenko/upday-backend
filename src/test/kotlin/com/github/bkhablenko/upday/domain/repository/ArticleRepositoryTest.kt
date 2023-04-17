package com.github.bkhablenko.upday.domain.repository

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.support.toUUID
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.util.UUID.randomUUID

@DisplayName("ArticleRepository")
class ArticleRepositoryTest : AbstractDataJpaTest() {

    companion object {
        private val ARTICLE_1 = "ee8db45b-c395-481d-809b-9af50d959908".toUUID()
    }

    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @DisplayName("findByIdWithAuthors")
    @Nested
    @Sql
    inner class FindByIdWithAuthorsTest {

        @Test
        fun `should return expected ArticleEntity`() {
            // Given
            val articleId = ARTICLE_1

            // When
            val foundEntity = articleRepository.findByIdWithAuthors(ARTICLE_1)

            // Then
            assertThat(foundEntity, not(nullValue()))
            foundEntity as ArticleEntity
            assertThat(foundEntity.id, equalTo(articleId))
            assertThat(foundEntity.title, equalTo("Article 1"))
        }

        @Test
        fun `should JOIN FETCH authors`() {
            // Given
            val articleId = ARTICLE_1

            // When
            val foundEntity = articleRepository.findByIdWithAuthors(articleId)

            // Then
            assertThat(foundEntity, not(nullValue()))
            assertThat(persistenceUnitUtil.isLoaded(foundEntity, "authors"), equalTo(true))
        }

        @Test
        fun `should return null if nothing found`() {
            // Given
            val articleId = randomUUID()

            // Expect
            assertThat(articleRepository.findByIdWithAuthors(articleId), nullValue())
        }
    }
}

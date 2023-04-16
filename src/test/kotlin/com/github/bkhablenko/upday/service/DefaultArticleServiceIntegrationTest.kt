package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.service.model.ArticleCriteria
import com.github.bkhablenko.upday.support.time.until
import com.github.bkhablenko.upday.support.toUUID
import jakarta.transaction.Transactional
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate
import java.time.Month.APRIL

@Transactional
class DefaultArticleServiceIntegrationTest : AbstractIntegrationTest() {

    companion object {
        private val AUTHOR_1 = "3e6806e9-8605-4438-b843-b929379c48ff".toUUID()

        private val ARTICLE_1 = "ee8db45b-c395-481d-809b-9af50d959908".toUUID()
        private val ARTICLE_2 = "b32d4855-4df7-4bc2-9a91-4fce14828b1f".toUUID()
        private val ARTICLE_3 = "c352907f-1429-47df-b67d-8f99ca050674".toUUID()
    }

    @Autowired
    private lateinit var articleService: ArticleService

    @Nested
    inner class SearchArticlesTest {

        @Test
        @Sql
        fun testSearchArticlesByAuthorId() {
            // Given
            val criteria = ArticleCriteria(authorId = AUTHOR_1)

            // When
            val foundEntities = articleService.searchArticles(criteria)

            // Then
            assertThat(foundEntities.map { it.id }, containsInAnyOrder(ARTICLE_1, ARTICLE_3))
        }

        @Test
        @Sql
        fun testSearchArticlesByTags() {
            // Given
            val criteria = ArticleCriteria(tags = listOf("health", "politics"))

            // When
            val foundEntities = articleService.searchArticles(criteria)

            // Then
            assertThat(foundEntities.map { it.id }, containsInAnyOrder(ARTICLE_1, ARTICLE_2, ARTICLE_3))
        }

        @Test
        @Sql
        fun testSearchArticlesByCreatedDateRange() {
            // Given
            val createdDateRange = LocalDate.of(2023, APRIL, 10) until LocalDate.of(2023, APRIL, 30)
            val criteria = ArticleCriteria(createdDateRange = createdDateRange)

            // When
            val foundEntities = articleService.searchArticles(criteria)

            // Then
            assertThat(foundEntities.map { it.id }, containsInAnyOrder(ARTICLE_1, ARTICLE_2))
        }

        @Test
        @Sql
        fun testSearchArticlesSortedByCreatedDateDesc() {
            // Given
            val criteria = ArticleCriteria()

            // When
            val foundEntities = articleService.searchArticles(criteria)

            // Then
            assertThat(foundEntities.map { it.id }, contains(ARTICLE_3, ARTICLE_2, ARTICLE_1))
        }
    }
}

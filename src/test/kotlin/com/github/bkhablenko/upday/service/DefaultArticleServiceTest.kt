package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.repository.ArticleRepository
import com.github.bkhablenko.upday.exception.ArticleNotFoundException
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.UUID.randomUUID

@DisplayName("DefaultArticleService")
class DefaultArticleServiceTest {

    companion object {
        private val ARTICLE_ID = randomUUID()
    }

    private val articleRepository = mock<ArticleRepository>()

    private val articleService = DefaultArticleService(articleRepository)

    @DisplayName("getArticleById")
    @Nested
    inner class GetArticleByIdTest {

        @Test
        fun `should return expected ArticleEntity if found`() {
            // Given
            val expectedEntity = ArticleEntity("", "", "", emptyList(), emptyList()).apply { id = ARTICLE_ID }
            whenever(articleRepository.findByIdWithAuthors(ARTICLE_ID)) doReturn expectedEntity

            // Expect
            assertThat(articleService.getArticleById(ARTICLE_ID), sameInstance(expectedEntity))
        }

        @Test
        fun `should throw exception if not found`() {
            // Given
            whenever(articleRepository.findByIdWithAuthors(ARTICLE_ID)) doReturn null

            // Expect
            assertThrows<ArticleNotFoundException> {
                articleService.getArticleById(ARTICLE_ID)
            }
        }
    }
}

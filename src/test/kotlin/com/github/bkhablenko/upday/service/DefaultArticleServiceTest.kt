package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.domain.repository.ArticleRepository
import com.github.bkhablenko.upday.domain.repository.AuthorRepository
import com.github.bkhablenko.upday.exception.ArticleNotFoundException
import com.github.bkhablenko.upday.exception.AuthorNotFoundException
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.same
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional
import java.util.UUID
import java.util.UUID.randomUUID

@DisplayName("DefaultArticleService")
class DefaultArticleServiceTest {

    private val articleRepository = mock<ArticleRepository>()
    private val authorRepository = mock<AuthorRepository>()

    private val articleService = DefaultArticleService(articleRepository, authorRepository)

    @DisplayName("createArticle")
    @Nested
    inner class CreateArticleTest {

        @Test
        fun `should save ArticleEntity`() {
            // Given
            val article = ArticleEntity(
                title = "Song of the Sausage Creature",
                description = "A wild ride on a Ducati, celebrating the thrill and culture of motorcycling.",
                body = "(Just some non-empty string.)",
                tags = listOf("motorcycles"),
            )
            whenever(articleRepository.save(any())) doAnswer { it.arguments[0] as ArticleEntity }

            // Given
            val authorId = randomUUID()
            val author = AuthorEntity("Hunter S. Thompson").apply { id = authorId }
            whenever(authorRepository.findById(authorId)) doReturn Optional.of(author)

            // When
            articleService.createArticle(article, listOf(authorId))

            // Then
            verify(articleRepository).save(check {
                assertThat(it.title, equalTo(article.title))
                assertThat(it.description, equalTo(article.description))
                assertThat(it.body, equalTo(article.body))
                assertThat(it.tags, equalTo(article.tags))
                assertThat(it.authors, contains(sameInstance(author)))
            })
        }

        @Test
        fun `should throw exception if author not found`() {
            // Given
            val authorId = randomUUID()
            whenever(authorRepository.findById(authorId)) doReturn Optional.empty()

            // Expect
            assertThrows<AuthorNotFoundException> {
                articleService.createArticle(emptyArticle(), listOf(authorId))
            }
        }
    }

    @DisplayName("deleteArticle")
    @Nested
    inner class DeleteArticleTest {

        @Test
        fun `should delete ArticleEntity if found`() {
            // Given
            val articleId = randomUUID()
            val article = emptyArticle(id = articleId)
            whenever(articleRepository.findById(articleId)) doReturn Optional.of(article)

            // When
            articleService.deleteArticleById(articleId)

            // Then
            verify(articleRepository).delete(same(article))
        }

        @Test
        fun `should throw exception if not found`() {
            // Given
            val articleId = randomUUID()
            whenever(articleRepository.findById(articleId)) doReturn Optional.empty()

            // Expect
            assertThrows<ArticleNotFoundException> {
                articleService.deleteArticleById(articleId)
            }
        }
    }

    @DisplayName("getArticleById")
    @Nested
    inner class GetArticleByIdTest {

        @Test
        fun `should return expected ArticleEntity if found`() {
            // Given
            val articleId = randomUUID()
            val expectedEntity = emptyArticle(id = articleId)
            whenever(articleRepository.findByIdWithAuthors(articleId)) doReturn expectedEntity

            // Expect
            assertThat(articleService.getArticleById(articleId), sameInstance(expectedEntity))
        }

        @Test
        fun `should throw exception if not found`() {
            // Given
            val articleId = randomUUID()
            whenever(articleRepository.findByIdWithAuthors(articleId)) doReturn null

            // Expect
            assertThrows<ArticleNotFoundException> {
                articleService.getArticleById(articleId)
            }
        }
    }

    private fun emptyArticle(id: UUID? = null): ArticleEntity {
        val article = ArticleEntity("", "", "", emptyList(), emptyList())
        id?.let { article.id = it }
        return article
    }
}

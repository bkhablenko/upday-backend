package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.domain.repository.ArticleRepository
import com.github.bkhablenko.upday.domain.repository.AuthorRepository
import com.github.bkhablenko.upday.exception.ArticleNotFoundException
import com.github.bkhablenko.upday.exception.AuthorNotFoundException
import com.github.bkhablenko.upday.service.model.CreateArticleModel
import com.github.bkhablenko.upday.service.model.UpdateArticleModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID.randomUUID

@DisplayName("DefaultArticleService")
class DefaultArticleServiceTest {

    private val articleRepository = mock<ArticleRepository> {
        on { save(any()) } doAnswer { it.arguments[0] as ArticleEntity }
    }
    private val authorRepository = mock<AuthorRepository>()

    private val articleService = DefaultArticleService(articleRepository, authorRepository)

    @DisplayName("createArticle")
    @Nested
    inner class CreateArticleTest {

        @Test
        fun `should save ArticleEntity`() {
            val authorId = randomUUID()
            val author = someAuthor { id = authorId }
            whenever(authorRepository.findById(authorId)) doReturn Optional.of(author)

            val expectedValues = CreateArticleModel(
                title = "Song of the Sausage Creature",
                description = "A wild ride on a Ducati, celebrating the thrill and culture of motorcycling.",
                body = "Some fancy text goes here.",
                tags = listOf("motorcycles"),
                authors = listOf(authorId),
            )
            articleService.createArticle(expectedValues)

            verify(articleRepository).save(check {
                assertThat(it.title, equalTo(expectedValues.title))
                assertThat(it.description, equalTo(expectedValues.description))
                assertThat(it.body, equalTo(expectedValues.body))
                assertThat(it.tags, equalTo(expectedValues.tags))
                assertThat(it.authors, contains(author))
            })
        }

        @Test
        fun `should throw exception if any AuthorEntity not found`() {
            val authorId = randomUUID()
            whenever(authorRepository.findById(authorId)) doReturn Optional.empty()

            assertThrows<AuthorNotFoundException> {
                articleService.createArticle(CreateArticleModel("", "", "", emptyList(), listOf(authorId)))
            }
            verify(articleRepository, never()).save(any())
        }
    }

    @DisplayName("updateArticle")
    @Nested
    inner class UpdateArticleTest {

        @Test
        fun `should save ArticleEntity with updates if provided`() {
            val articleId = randomUUID()
            val article = someArticle {
                id = articleId
                val timestamp = LocalDateTime.now()
                createdDate = timestamp
                lastModifiedDate = timestamp
            }
            whenever(articleRepository.findByIdWithAuthors(articleId)) doReturn article

            val authorId = randomUUID()
            val author = someAuthor { id = authorId }
            whenever(authorRepository.findById(authorId)) doReturn Optional.of(author)

            val expectedValues = UpdateArticleModel(
                title = "Song of the Sausage Creature",
                description = "A wild ride on a Ducati, celebrating the thrill and culture of motorcycling.",
                body = "Some fancy text goes here.",
                tags = listOf("motorcycles"),
                authors = listOf(authorId)
            )
            articleService.updateArticle(articleId, expectedValues)

            verify(articleRepository).save(check {
                assertThat(it.id, equalTo(article.id))

                assertThat(it.title, equalTo(expectedValues.title))
                assertThat(it.description, equalTo(expectedValues.description))
                assertThat(it.body, equalTo(expectedValues.body))
                assertThat(it.tags, equalTo(expectedValues.tags))
                assertThat(it.authors, contains(author))

                assertThat(it.createdDate, equalTo(article.createdDate))
                assertThat(it.lastModifiedDate, equalTo(article.lastModifiedDate))
            })
        }

        @Test
        fun `should save ArticleEntity without updates if not provided`() {
            val articleId = randomUUID()
            val article = someArticle {
                id = articleId
                val timestamp = LocalDateTime.now()
                createdDate = timestamp
                lastModifiedDate = timestamp
            }
            whenever(articleRepository.findByIdWithAuthors(articleId)) doReturn article

            articleService.updateArticle(articleId, UpdateArticleModel())

            verify(articleRepository).save(check {
                assertThat(it.id, equalTo(article.id))

                assertThat(it.title, equalTo(article.title))
                assertThat(it.description, equalTo(article.description))
                assertThat(it.body, equalTo(article.body))
                assertThat(it.tags, equalTo(article.tags))
                assertThat(it.authors, equalTo(article.authors))

                assertThat(it.createdDate, equalTo(article.createdDate))
                assertThat(it.lastModifiedDate, equalTo(article.lastModifiedDate))
            })
        }

        @Test
        fun `should throw exception if ArticleEntity not found`() {
            val articleId = randomUUID()
            whenever(articleRepository.findByIdWithAuthors(articleId)) doReturn null

            assertThrows<ArticleNotFoundException> {
                articleService.updateArticle(articleId, UpdateArticleModel())
            }
            verify(articleRepository, never()).save(any())
        }

        @Test
        fun `should throw exception if any AuthorEntity not found`() {
            val articleId = randomUUID()
            whenever(articleRepository.findByIdWithAuthors(articleId)) doReturn someArticle { id = articleId }

            val authorId = randomUUID()
            whenever(authorRepository.findById(authorId)) doReturn Optional.empty()

            assertThrows<AuthorNotFoundException> {
                articleService.updateArticle(articleId, UpdateArticleModel(authors = listOf(authorId)))
            }
            verify(articleRepository, never()).save(any())
        }
    }

    @DisplayName("deleteArticle")
    @Nested
    inner class DeleteArticleTest {

        @Test
        fun `should delete ArticleEntity if found`() {
            // Given
            val articleId = randomUUID()
            val article = someArticle { id = articleId }
            whenever(articleRepository.findById(articleId)) doReturn Optional.of(article)

            // When
            articleService.deleteArticleById(articleId)

            // Then
            verify(articleRepository).delete(article)
        }

        @Test
        fun `should throw exception if ArticleEntity not found`() {
            // Given
            val articleId = randomUUID()
            whenever(articleRepository.findById(articleId)) doReturn Optional.empty()

            // Expect
            assertThrows<ArticleNotFoundException> {
                articleService.deleteArticleById(articleId)
            }
            verify(articleRepository, never()).delete(any())
        }
    }

    @DisplayName("getArticleById")
    @Nested
    inner class GetArticleByIdTest {

        @Test
        fun `should return ArticleEntity if found`() {
            // Given
            val articleId = randomUUID()
            val expectedEntity = someArticle { id = articleId }
            whenever(articleRepository.findByIdWithAuthors(articleId)) doReturn expectedEntity

            // Expect
            assertThat(articleService.getArticleById(articleId), equalTo(expectedEntity))
        }

        @Test
        fun `should throw exception if ArticleEntity not found`() {
            // Given
            val articleId = randomUUID()
            whenever(articleRepository.findByIdWithAuthors(articleId)) doReturn null

            // Expect
            assertThrows<ArticleNotFoundException> {
                articleService.getArticleById(articleId)
            }
        }
    }

    private fun someArticle(block: ArticleEntity.() -> Unit): ArticleEntity {
        val article = ArticleEntity("", "", "", emptyList(), emptyList())
        return article.apply(block)
    }

    private fun someAuthor(block: AuthorEntity.() -> Unit) = AuthorEntity("").apply(block)
}

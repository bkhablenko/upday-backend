package com.github.bkhablenko.upday.domain.repository

import com.github.bkhablenko.upday.domain.model.idList
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.anyAuthorIdEquals
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.createdDateGreaterThanOrEquals
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.createdDateLessThan
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.tagsContainAny
import com.github.bkhablenko.upday.support.toUUID
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate
import java.time.Month

class ArticleRepositoryQuerydslTest : AbstractDataJpaTest() {

    companion object{
        private val AUTHOR_1 = "3e6806e9-8605-4438-b843-b929379c48ff".toUUID()

        private val ARTICLE_1 = "ee8db45b-c395-481d-809b-9af50d959908".toUUID()
        private val ARTICLE_2 = "b32d4855-4df7-4bc2-9a91-4fce14828b1f".toUUID()
        private val ARTICLE_3 = "c352907f-1429-47df-b67d-8f99ca050674".toUUID()
    }

    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @Test
    @Sql
    fun testAnyAuthorIdEquals() {
        // Given
        val authorId = AUTHOR_1

        // When
        val foundEntities = articleRepository.findAll(anyAuthorIdEquals(authorId))

        // Then
        assertThat(foundEntities.idList, containsInAnyOrder(ARTICLE_1, ARTICLE_3))
    }

    @Test
    @Sql
    fun testTagsContainAny() {
        // Given
        val tags = listOf("health", "politics")

        // When
        val foundEntities = articleRepository.findAll(tagsContainAny(tags))

        // Then
        assertThat(foundEntities.idList, containsInAnyOrder(ARTICLE_1, ARTICLE_2, ARTICLE_3))
    }

    @Test
    @Sql
    fun testCreatedDateGreaterThanOrEquals() {
        // Given
        val createdDate = LocalDate.of(2023, Month.APRIL, 10)

        // When
        val foundEntities = articleRepository.findAll(createdDateGreaterThanOrEquals(createdDate))

        // Then
        assertThat(foundEntities.idList, containsInAnyOrder(ARTICLE_2, ARTICLE_3))
    }

    @Test
    @Sql
    fun testCreatedDateLessThan() {
        // Given
        val createdDate = LocalDate.of(2023, Month.APRIL, 10)

        // When
        val foundEntities = articleRepository.findAll(createdDateLessThan(createdDate))

        // Then
        assertThat(foundEntities.idList, contains(ARTICLE_1))
    }
}

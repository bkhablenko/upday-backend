package com.github.bkhablenko.upday.domain.repository

import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.anyAuthorIdEquals
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.createdDateGreaterThanOrEquals
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.createdDateLessThan
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.tagsContainAny
import com.github.bkhablenko.upday.support.toUUID
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate
import java.time.Month

class ArticleRepositoryQuerydslTest : AbstractDataJpaTest() {

    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @Test
    @Sql
    fun testAnyAuthorIdEquals() {
        // Given
        val authorId = "3e6806e9-8605-4438-b843-b929379c48ff".toUUID() // Author 1

        // When
        val foundEntities = articleRepository.findAll(anyAuthorIdEquals(authorId))

        // Then
        assertThat(
            foundEntities.map { it.id }, containsInAnyOrder(
                "ee8db45b-c395-481d-809b-9af50d959908".toUUID(), // Article 1
                "c352907f-1429-47df-b67d-8f99ca050674".toUUID(), // Article 3
            )
        )
    }

    @Test
    @Sql
    fun testTagsContainAny() {
        // Given
        val tags = listOf("health", "politics")

        // When
        val foundEntities = articleRepository.findAll(tagsContainAny(tags))

        // Then
        assertThat(
            foundEntities.map { it.id }, containsInAnyOrder(
                "ee8db45b-c395-481d-809b-9af50d959908".toUUID(), // Article 1
                "b32d4855-4df7-4bc2-9a91-4fce14828b1f".toUUID(), // Article 2
                "c352907f-1429-47df-b67d-8f99ca050674".toUUID(), // Article 3
            )
        )
    }

    @Test
    @Sql
    fun testCreatedDateGreaterThanOrEquals() {
        // Given
        val createdDate = LocalDate.of(2023, Month.APRIL, 10)

        // When
        val foundEntities = articleRepository.findAll(createdDateGreaterThanOrEquals(createdDate))

        // Then
        assertThat(
            foundEntities.map { it.id }, containsInAnyOrder(
                "b32d4855-4df7-4bc2-9a91-4fce14828b1f".toUUID(), // Article 2
                "c352907f-1429-47df-b67d-8f99ca050674".toUUID(), // Article 3
            )
        )
    }

    @Test
    @Sql
    fun testCreatedDateLessThan() {
        // Given
        val createdDate = LocalDate.of(2023, Month.APRIL, 10)

        // When
        val foundEntities = articleRepository.findAll(createdDateLessThan(createdDate))

        // Then
        assertThat(
            foundEntities.map { it.id }, containsInAnyOrder(
                "ee8db45b-c395-481d-809b-9af50d959908".toUUID(), // Article 1
            )
        )
    }
}

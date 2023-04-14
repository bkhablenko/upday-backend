package com.github.bkhablenko.upday.domain.repository

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate
import java.time.Month
import java.util.UUID

@DisplayName("ArticleRepository")
class ArticleRepositoryTest : AbstractDataJpaTest() {

    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @DisplayName("findAllByAuthorsId")
    @Nested
    inner class FindAllByAuthorsIdTest {

        @Test
        @Sql
        fun `should find all articles by author ID`() {
            val authorId = UUID.fromString("3e6806e9-8605-4438-b843-b929379c48ff")
            val foundEntities = articleRepository.findAllByAuthorsId(authorId)
            assertThat(
                foundEntities.map { it.id }, containsInAnyOrder(
                    UUID.fromString("ee8db45b-c395-481d-809b-9af50d959908"), // Article X
                    UUID.fromString("b32d4855-4df7-4bc2-9a91-4fce14828b1f"), // Article Y
                )
            )
        }
    }

    @DisplayName("findAllByCreatedDateBetween")
    @Nested
    inner class FindAllByCreatedDateBetweenTest {

        @Test
        @Sql
        fun `should find all articles between two dates`() {
            val foundEntities = articleRepository.findAllByCreatedDateBetween(
                LocalDate.of(2023, Month.APRIL, 10).atStartOfDay(),
                LocalDate.of(2023, Month.APRIL, 20).atStartOfDay(),
            )
            assertThat(foundEntities, hasSize(1))

            val expectedEntityId = UUID.fromString("dfdb9f4a-4a62-47a2-807b-198725225d47") // Article Y
            assertThat(foundEntities[0].id, equalTo(expectedEntityId))
        }
    }

    @DisplayName("findAllByTagsContaining")
    @Nested
    inner class FindAllByTagsContaining {

        @Test
        @Sql
        fun `should find all articles with tag`() {
            val foundEntities = articleRepository.findAllByTagsContaining("health")
            assertThat(foundEntities, hasSize(2))
            assertThat(
                foundEntities.map { it.id }, containsInAnyOrder(
                    UUID.fromString("e9231b30-63cb-4c82-8b74-9511a32cc21d"), // Article X
                    UUID.fromString("dfdb9f4a-4a62-47a2-807b-198725225d47"), // Article Y
                )
            )
        }
    }
}

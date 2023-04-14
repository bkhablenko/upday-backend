package com.github.bkhablenko.upday.domain.model

import com.github.bkhablenko.upday.domain.repository.AbstractDataJpaTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ArticleEntity")
class ArticleEntityJpaMappingsTest : AbstractDataJpaTest() {

    @Test
    fun `should have correct JPA mappings`() {
        val author = entityManager.persist(AuthorEntity("John Smith"))
        entityManager.persistAndFlush(
            ArticleEntity(
                title = "(Almost) Empty Article",
                description = "Testing JPA mappings.",
                body = "",
                tags = listOf("test"),
                authors = listOf(author),
            )
        )
    }
}

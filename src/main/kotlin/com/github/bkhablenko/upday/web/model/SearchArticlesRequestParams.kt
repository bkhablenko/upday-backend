package com.github.bkhablenko.upday.web.model

import com.github.bkhablenko.upday.service.model.ArticleCriteria
import com.github.bkhablenko.upday.support.time.until
import java.time.LocalDate
import java.time.Month
import java.util.UUID

data class SearchArticlesRequestParams(
    val authorId: UUID? = null,
    val tags: List<String>? = null,

    // See PostgreSQL limitations:
    // https://www.postgresql.org/docs/current/datatype-datetime.html
    val publicationDateStart: LocalDate = LocalDate.of(1_000, Month.JANUARY, 1),
    val publicationDateEndExclusive: LocalDate = LocalDate.of(3_000, Month.JANUARY, 1)
) {

    fun toArticleCriteria() = ArticleCriteria(
        authorId = authorId,
        tags = tags,
        createdDateRange = publicationDateStart until publicationDateEndExclusive,
    )
}

package com.github.bkhablenko.upday.service.model

import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.anyAuthorIdEquals
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.createdDateGreaterThanOrEquals
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.createdDateLessThan
import com.github.bkhablenko.upday.domain.querydsl.ArticlePredicates.tagsContainAny
import com.github.bkhablenko.upday.support.time.LocalDateRange
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import java.util.UUID

data class ArticleCriteria(
    val authorId: UUID? = null,
    val tags: List<String>? = null,
    val createdDateRange: LocalDateRange? = null,
) {

    fun toPredicate(): Predicate {
        val where = BooleanBuilder()
        authorId?.let {
            where.and(anyAuthorIdEquals(it))
        }
        tags?.let {
            where.and(tagsContainAny(it))
        }
        createdDateRange?.let {
            where.and(createdDateGreaterThanOrEquals(it.start))
            where.and(createdDateLessThan(it.endExclusive))
        }
        return where
    }
}

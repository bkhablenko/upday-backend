package com.github.bkhablenko.upday.domain.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions.booleanTemplate
import java.time.LocalDate
import java.util.UUID
import com.github.bkhablenko.upday.domain.model.QArticleEntity.articleEntity as article

object ArticlePredicates {

    private const val TAGS_CONTAIN_ANY_TEMPLATE = "arrayoverlap({0}, string_to_array({1}, ', ')) = true"

    fun anyAuthorIdEquals(authorId: UUID): BooleanExpression {
        return article.authors.any().id.eq(authorId)
    }

    fun tagsContainAny(tags: List<String>): BooleanExpression {
        return booleanTemplate(TAGS_CONTAIN_ANY_TEMPLATE, article.tags, tags.joinToString())
    }

    fun createdDateGreaterThanOrEquals(createdDate: LocalDate): BooleanExpression {
        return article.createdDate.goe(createdDate.atStartOfDay())
    }

    fun createdDateLessThan(createdDate: LocalDate): BooleanExpression {
        return article.createdDate.lt(createdDate.atStartOfDay())
    }
}

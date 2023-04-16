package com.github.bkhablenko.upday.domain.repository

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import java.util.UUID

// TODO(bkhablenko): Refactor as a custom repository
@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, UUID>, QuerydslPredicateExecutor<ArticleEntity> {

    // Temporary solution to https://stackoverflow.com/questions/43066169
    @EntityGraph(attributePaths = ["authors"])
    override fun findAll(predicate: Predicate, sort: Sort): Iterable<ArticleEntity>
}

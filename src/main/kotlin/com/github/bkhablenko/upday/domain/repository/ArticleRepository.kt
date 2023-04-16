package com.github.bkhablenko.upday.domain.repository

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, UUID>, QuerydslPredicateExecutor<ArticleEntity> {

    fun findAllByAuthorsId(authorId: UUID): List<ArticleEntity>

    fun findAllByCreatedDateBetween(fromDate: LocalDateTime, toDate: LocalDateTime): List<ArticleEntity>

    @Query(value = """SELECT * FROM "article" WHERE :tag = ANY("tags")""", nativeQuery = true)
    fun findAllByTagsContaining(@Param("tag") tag: String): List<ArticleEntity>
}

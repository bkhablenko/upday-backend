package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.domain.repository.ArticleRepository
import com.github.bkhablenko.upday.domain.repository.AuthorRepository
import com.github.bkhablenko.upday.exception.ArticleNotFoundException
import com.github.bkhablenko.upday.exception.AuthorNotFoundException
import com.github.bkhablenko.upday.service.model.ArticleCriteria
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DefaultArticleService(
    private val articleRepository: ArticleRepository,
    private val authorRepository: AuthorRepository,
) : ArticleService {

    companion object {
        private val SORT_BY_CREATED_DATE_DESC = Sort.by(Direction.DESC, "createdDate")
    }

    @Transactional
    override fun createArticle(article: ArticleEntity, authors: List<UUID>): ArticleEntity {
        return articleRepository.save(with(article) {
            ArticleEntity(
                title = title,
                description = description,
                body = body,
                tags = tags,
                authors = authors.map { findAuthorById(it) },
            )
        })
    }

    override fun getArticleById(articleId: UUID): ArticleEntity {
        return articleRepository.findByIdWithAuthors(articleId) ?: throw ArticleNotFoundException(articleId)
    }

    override fun searchArticles(criteria: ArticleCriteria): List<ArticleEntity> {
        val predicate = criteria.toPredicate()
        return findAllFetchJoinAuthorsSortByCreatedDateDesc(predicate)
    }

    // TODO(bkhablenko): This logic should be part of the repository
    private fun findAllFetchJoinAuthorsSortByCreatedDateDesc(predicate: Predicate): List<ArticleEntity> {
        return articleRepository
            .findAll(predicate, SORT_BY_CREATED_DATE_DESC)
            .toList()
    }

    private fun findAuthorById(authorId: UUID): AuthorEntity {
        return authorRepository.findByIdOrNull(authorId) ?: throw AuthorNotFoundException(authorId)
    }
}

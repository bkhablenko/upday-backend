package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.domain.repository.ArticleRepository
import com.github.bkhablenko.upday.domain.repository.AuthorRepository
import com.github.bkhablenko.upday.exception.ArticleNotFoundException
import com.github.bkhablenko.upday.exception.AuthorNotFoundException
import com.github.bkhablenko.upday.service.model.ArticleCriteria
import com.github.bkhablenko.upday.service.model.CreateArticleModel
import com.github.bkhablenko.upday.service.model.UpdateArticleModel
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class DefaultArticleService(
    private val articleRepository: ArticleRepository,
    private val authorRepository: AuthorRepository,
) : ArticleService {

    companion object {
        private val SORT_BY_CREATED_DATE_DESC = Sort.by(Direction.DESC, "createdDate")
    }

    override fun createArticle(createModel: CreateArticleModel): ArticleEntity {
        return articleRepository.save(with(createModel) {
            ArticleEntity(
                title = title,
                description = description,
                body = body,
                tags = tags,
                authors = authors.map { findAuthorById(it) },
            )
        })
    }

    override fun updateArticle(articleId: UUID, updateModel: UpdateArticleModel): ArticleEntity {
        val article = articleRepository.findByIdWithAuthors(articleId) ?: throw ArticleNotFoundException(articleId)
        with(updateModel) {
            title?.let { article.title = it }
            description?.let { article.description = it }
            body?.let { article.body = it }
            tags?.let { article.tags = it }
            authors?.map { findAuthorById(it) }?.let { article.authors = it }
        }
        return articleRepository.save(article)
    }

    override fun deleteArticleById(articleId: UUID) {
        with(articleRepository) {
            findByIdOrNull(articleId)?.let { delete(it) } ?: throw ArticleNotFoundException(articleId)
        }
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

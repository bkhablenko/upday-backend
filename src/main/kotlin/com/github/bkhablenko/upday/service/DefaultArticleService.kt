package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.repository.ArticleRepository
import com.github.bkhablenko.upday.exception.ArticleNotFoundException
import com.github.bkhablenko.upday.service.model.ArticleCriteria
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DefaultArticleService(private val articleRepository: ArticleRepository) : ArticleService {

    companion object {
        private val SORT_BY_CREATED_DATE_DESC = Sort.by(Direction.DESC, "createdDate")
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
}

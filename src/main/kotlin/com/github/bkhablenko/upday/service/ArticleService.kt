package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.service.model.ArticleCriteria
import java.util.UUID

interface ArticleService {

    fun getArticleById(articleId: UUID): ArticleEntity

    fun searchArticles(criteria: ArticleCriteria): List<ArticleEntity>
}

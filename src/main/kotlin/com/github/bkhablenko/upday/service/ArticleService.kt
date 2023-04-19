package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.service.model.ArticleCriteria
import com.github.bkhablenko.upday.service.model.CreateArticleModel
import com.github.bkhablenko.upday.service.model.UpdateArticleModel
import java.util.UUID

interface ArticleService {

    fun createArticle(createModel: CreateArticleModel): ArticleEntity

    fun updateArticle(articleId: UUID, updateModel: UpdateArticleModel): ArticleEntity

    fun deleteArticleById(articleId: UUID)

    fun getArticleById(articleId: UUID): ArticleEntity

    fun searchArticles(criteria: ArticleCriteria): List<ArticleEntity>
}

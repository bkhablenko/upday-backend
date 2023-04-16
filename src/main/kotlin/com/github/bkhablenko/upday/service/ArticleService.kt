package com.github.bkhablenko.upday.service

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.service.model.ArticleCriteria

interface ArticleService {

    fun searchArticles(criteria: ArticleCriteria): List<ArticleEntity>
}

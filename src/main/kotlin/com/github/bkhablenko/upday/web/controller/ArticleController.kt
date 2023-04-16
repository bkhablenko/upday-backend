package com.github.bkhablenko.upday.web.controller

import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import com.github.bkhablenko.upday.service.ArticleService
import com.github.bkhablenko.upday.web.model.ArticleItem
import com.github.bkhablenko.upday.web.model.AuthorItem
import com.github.bkhablenko.upday.web.model.SearchArticlesRequestParams
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(private val articleService: ArticleService) {

    @GetMapping
    fun searchArticles(requestParams: SearchArticlesRequestParams): List<ArticleItem> {
        return articleService
            .searchArticles(requestParams.toArticleCriteria())
            .map { it.toArticleItem() }
    }

    private fun ArticleEntity.toArticleItem() = ArticleItem(
        id = id,
        title = title,
        description = description,
        body = body,
        tags = tags,
        publicationDate = createdDate.toLocalDate(),
        authors = authors.map { it.toAuthorItem() }
    )

    private fun AuthorEntity.toAuthorItem() = AuthorItem(id, fullName)
}

package com.github.bkhablenko.upday.web.controller

import com.github.bkhablenko.upday.service.ArticleService
import com.github.bkhablenko.upday.web.model.GetArticleByIdResponse
import com.github.bkhablenko.upday.web.model.SearchArticlesRequestParams
import com.github.bkhablenko.upday.web.model.SearchArticlesResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(private val articleService: ArticleService) {

    @GetMapping("/{articleId}")
    fun getArticleById(@PathVariable articleId: UUID): GetArticleByIdResponse {
        val article = articleService.getArticleById(articleId)

        return GetArticleByIdResponse.of(article)
    }

    @GetMapping
    fun searchArticles(requestParams: SearchArticlesRequestParams): SearchArticlesResponse {
        val articles = articleService.searchArticles(requestParams.toArticleCriteria())

        return SearchArticlesResponse.of(articles)
    }
}

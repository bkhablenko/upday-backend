package com.github.bkhablenko.upday.web.controller

import com.github.bkhablenko.upday.service.ArticleService
import com.github.bkhablenko.upday.web.model.GetArticleByIdResponse
import com.github.bkhablenko.upday.web.model.PublishArticleRequest
import com.github.bkhablenko.upday.web.model.PublishArticleResponse
import com.github.bkhablenko.upday.web.model.SearchArticlesRequestParams
import com.github.bkhablenko.upday.web.model.SearchArticlesResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(private val articleService: ArticleService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('EDITOR')")
    fun publishArticle(@RequestBody payload: PublishArticleRequest): PublishArticleResponse {
        val article = articleService.createArticle(payload.toArticleEntity(), payload.authors)

        return PublishArticleResponse.of(article)
    }

    // TODO(bkhablenko): Implement PUT /api/v1/articles/:id

    @DeleteMapping("/{articleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('EDITOR')")
    fun removeArticle(@PathVariable articleId: UUID) {
        articleService.deleteArticleById(articleId)
    }

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

package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.domain.model.ArticleEntity
import java.util.UUID

data class PublishArticleRequest(

    @JsonProperty("title")
    val title: String,

    @JsonProperty("description")
    val description: String,

    @JsonProperty("body")
    val body: String,

    @JsonProperty("tags")
    val tags: List<String>,

    @JsonProperty("authors")
    val authors: List<UUID>,
) {

    fun toArticleEntity() = ArticleEntity(title, description, body, tags)
}

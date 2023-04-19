package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.service.model.CreateArticleModel

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
    val authors: List<Id>,
) {

    fun toArticleModel() = CreateArticleModel(title, description, body, tags, authors.map { it.value })
}

package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.service.model.CreateArticleModel
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class PublishArticleRequest(

    @JsonProperty("title")
    val title: String,

    @JsonProperty("description")
    val description: String,

    @JsonProperty("body")
    val body: String,

    @JsonProperty("tags")
    val tags: List<@Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$") String> = emptyList(),

    @JsonProperty("authors")
    @field:Size(min = 1)
    val authors: List<Id>,
) {

    fun toArticleModel() = CreateArticleModel(title, description, body, tags, authors.map { it.value })
}

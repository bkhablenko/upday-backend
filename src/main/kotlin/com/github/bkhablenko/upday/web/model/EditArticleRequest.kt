package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.service.model.UpdateArticleModel

data class EditArticleRequest(

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("body")
    val body: String? = null,

    @JsonProperty("tags")
    val tags: List<String>? = null,

    @JsonProperty("authors")
    val authors: List<Id>? = null,
) {

    fun toArticleModel() = UpdateArticleModel(title, description, body, tags, authors?.map { it.value })
}

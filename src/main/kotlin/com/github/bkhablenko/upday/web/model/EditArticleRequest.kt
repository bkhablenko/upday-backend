package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.service.model.UpdateArticleModel
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class EditArticleRequest(

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("body")
    val body: String? = null,

    @JsonProperty("tags")
    val tags: List<@Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$") String>? = null,

    @JsonProperty("authors")
    @field:Size(min = 1)
    val authors: List<Id>? = null,
) {

    fun toArticleModel() = UpdateArticleModel(title, description, body, tags, authors?.map { it.value })
}

package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.util.UUID

data class ArticleItem(

    @JsonProperty("id")
    val id: UUID,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("description")
    val description: String,

    @JsonProperty("body")
    val body: String,

    @JsonProperty("tags")
    val tags: List<String>,

    @JsonProperty("publicationDate")
    val publicationDate: LocalDate,

    @JsonProperty("authors")
    val authors: List<AuthorItem>,
)

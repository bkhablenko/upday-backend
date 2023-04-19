package com.github.bkhablenko.upday.service.model

import java.util.UUID

data class CreateArticleModel(

    val title: String,

    val description: String,

    val body: String,

    val tags: List<String>,

    val authors: List<UUID>,
)

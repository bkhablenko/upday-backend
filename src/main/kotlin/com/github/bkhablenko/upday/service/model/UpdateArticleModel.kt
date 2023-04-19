package com.github.bkhablenko.upday.service.model

import java.util.UUID

data class UpdateArticleModel(

    val title: String? = null,

    val description: String? = null,

    val body: String? = null,

    val tags: List<String>? = null,

    val authors: List<UUID>? = null,
)

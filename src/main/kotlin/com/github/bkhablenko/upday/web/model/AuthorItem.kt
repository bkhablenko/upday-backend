package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class AuthorItem(

    @JsonProperty("id")
    val id: UUID,

    @JsonProperty("fullName")
    val fullName: String,
)

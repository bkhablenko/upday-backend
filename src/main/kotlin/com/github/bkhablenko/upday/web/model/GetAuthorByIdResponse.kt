package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.domain.model.AuthorEntity

data class GetAuthorByIdResponse(

    @JsonProperty("id")
    val id: Id,

    @JsonProperty("fullName")
    val fullName: String,
) {

    companion object {
        fun of(authorEntity: AuthorEntity) = with(authorEntity) { GetAuthorByIdResponse(Id.encode(id), fullName) }
    }
}

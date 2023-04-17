package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import java.util.UUID

data class GetAuthorByIdResponse(

    @JsonProperty("id")
    val id: UUID,

    @JsonProperty("fullName")
    val fullName: String,
) {

    companion object {
        fun of(authorEntity: AuthorEntity) = with(authorEntity) { GetAuthorByIdResponse(id, fullName) }
    }
}

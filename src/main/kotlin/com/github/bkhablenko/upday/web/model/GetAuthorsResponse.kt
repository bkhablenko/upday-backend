package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.domain.model.AuthorEntity

data class GetAuthorsResponse(

    @JsonProperty("authors")
    val authors: List<Author>,
) {

    companion object {
        fun of(authorEntities: List<AuthorEntity>) = GetAuthorsResponse(authorEntities.map { Author.of(it) })
    }

    data class Author(

        @JsonProperty("id")
        val id: Id,

        @JsonProperty("fullName")
        val fullName: String,
    ) {

        companion object {
            fun of(authorEntity: AuthorEntity) = with(authorEntity) { Author(Id.encode(id), fullName) }
        }
    }
}

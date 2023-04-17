package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.upday.domain.model.ArticleEntity
import com.github.bkhablenko.upday.domain.model.AuthorEntity
import java.time.LocalDate

data class SearchArticlesResponse(

    @JsonProperty("articles")
    val articles: List<Article>,
) {

    companion object {
        fun of(articleEntities: List<ArticleEntity>) = SearchArticlesResponse(articleEntities.map { Article.of(it) })
    }

    data class Article(

        @JsonProperty("id")
        val id: Id,

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
        val authors: List<Author>,
    ) {

        companion object {
            fun of(articleEntity: ArticleEntity): Article {
                return with(articleEntity) {
                    Article(
                        id = Id.encode(id),
                        title = title,
                        description = description,
                        body = body,
                        tags = tags,
                        publicationDate = createdDate.toLocalDate(),
                        authors = authors.map { Author.of(it) },
                    )
                }
            }
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
}

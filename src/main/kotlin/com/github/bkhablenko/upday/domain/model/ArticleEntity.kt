package com.github.bkhablenko.upday.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "article")
data class ArticleEntity(

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "body")
    val body: String,

    @Column(name = "tags")
    val tags: List<String> = ArrayList(),

    @ManyToMany
    @JoinTable(
        name = "author_article",
        joinColumns = [JoinColumn(name = "article_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")],
    )
    val authors: List<AuthorEntity> = ArrayList()

) : AuditedEntity()

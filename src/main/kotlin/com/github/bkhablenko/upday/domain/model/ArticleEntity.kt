package com.github.bkhablenko.upday.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "article")
class ArticleEntity(

    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "body")
    var body: String,

    @Column(name = "tags")
    var tags: List<String> = ArrayList(),

    @ManyToMany
    @JoinTable(
        name = "author_article",
        joinColumns = [JoinColumn(name = "article_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")],
    )
    var authors: List<AuthorEntity> = ArrayList()

) : AuditedEntity()

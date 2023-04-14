package com.github.bkhablenko.upday.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "author")
data class AuthorEntity(

    @Column(name = "full_name")
    val fullName: String

) : AuditedEntity()

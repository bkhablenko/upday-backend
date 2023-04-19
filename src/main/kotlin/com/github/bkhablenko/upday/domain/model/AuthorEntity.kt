package com.github.bkhablenko.upday.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "author")
class AuthorEntity(

    @Column(name = "full_name")
    var fullName: String

) : AuditedEntity()

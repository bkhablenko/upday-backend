package com.github.bkhablenko.upday.domain.repository

import com.github.bkhablenko.upday.domain.model.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AuthorRepository : JpaRepository<AuthorEntity, UUID>

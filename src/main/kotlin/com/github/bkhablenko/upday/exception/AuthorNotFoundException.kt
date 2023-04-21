package com.github.bkhablenko.upday.exception

import java.util.UUID

class AuthorNotFoundException(authorId: UUID) : NotFoundException(authorId)

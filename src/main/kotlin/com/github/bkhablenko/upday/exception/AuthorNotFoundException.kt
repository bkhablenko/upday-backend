package com.github.bkhablenko.upday.exception

import java.util.UUID

class AuthorNotFoundException(val authorId: UUID) : NotFoundException("Author does not exist")

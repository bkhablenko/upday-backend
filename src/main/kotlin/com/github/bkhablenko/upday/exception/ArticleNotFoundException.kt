package com.github.bkhablenko.upday.exception

import java.util.UUID

class ArticleNotFoundException(articleId: UUID) : NotFoundException("Article [$articleId] does not exist")

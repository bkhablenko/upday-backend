package com.github.bkhablenko.upday.exception

import java.util.UUID

class ArticleNotFoundException(val articleId: UUID) : NotFoundException("Article does not exist")

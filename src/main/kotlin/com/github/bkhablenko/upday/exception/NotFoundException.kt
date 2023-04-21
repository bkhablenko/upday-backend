package com.github.bkhablenko.upday.exception

import java.util.UUID

abstract class NotFoundException(val resourceId: UUID) : RuntimeException()

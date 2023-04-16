package com.github.bkhablenko.upday.support

import java.util.UUID

fun String.toUUID(): UUID = UUID.fromString(this)

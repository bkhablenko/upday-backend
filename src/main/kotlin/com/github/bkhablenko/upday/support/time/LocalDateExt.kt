package com.github.bkhablenko.upday.support.time

import java.time.LocalDate

infix fun LocalDate.until(endExclusive: LocalDate) = LocalDateRange(this, endExclusive)

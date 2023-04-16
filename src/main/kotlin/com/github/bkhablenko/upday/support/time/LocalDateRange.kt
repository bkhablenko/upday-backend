package com.github.bkhablenko.upday.support.time

import java.time.LocalDate

@OptIn(ExperimentalStdlibApi::class)
data class LocalDateRange(
    override val start: LocalDate,
    override val endExclusive: LocalDate
) : OpenEndRange<LocalDate>

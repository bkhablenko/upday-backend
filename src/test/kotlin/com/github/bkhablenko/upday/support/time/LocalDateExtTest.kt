package com.github.bkhablenko.upday.support.time

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class LocalDateExtTest {

    @Test
    fun testUntil() {
        val expectedStart = LocalDate.of(2023, Month.APRIL, 10)
        val expectedEndExclusive = LocalDate.of(2023, Month.APRIL, 15)

        with(expectedStart until expectedEndExclusive) {
            assertThat(start, equalTo(expectedStart))
            assertThat(endExclusive, equalTo(expectedEndExclusive))
        }
    }
}

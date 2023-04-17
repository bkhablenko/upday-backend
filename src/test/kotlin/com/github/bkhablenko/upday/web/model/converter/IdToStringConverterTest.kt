package com.github.bkhablenko.upday.web.model.converter

import com.github.bkhablenko.upday.support.toUUID
import com.github.bkhablenko.upday.web.model.Id
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class IdToStringConverterTest {

    private val converter = IdToStringConverter()

    @Test
    fun testConvert() {
        val id = Id.encode("3e6806e9-8605-4438-b843-b929379c48ff".toUUID())
        assertThat(converter.convert(id), equalTo("8GXEDszDwrWFqBqTYGaJup"))
    }
}

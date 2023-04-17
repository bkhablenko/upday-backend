package com.github.bkhablenko.upday.web.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest

@JsonTest
class IdJsonTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `serialization and deserialization`() {
        val id = Id.random()

        with(objectMapper) {
            val json = writeValueAsString(id)
            assertThat(readValue<Id>(json), equalTo(id))
        }
    }
}

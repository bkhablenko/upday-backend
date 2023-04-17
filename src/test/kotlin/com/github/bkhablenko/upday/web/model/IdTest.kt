package com.github.bkhablenko.upday.web.model

import com.github.bkhablenko.upday.support.toUUID
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class IdTest {

    @Test
    fun testEncode() {
        val expectedValue = "3e6806e9-8605-4438-b843-b929379c48ff".toUUID()
        val expectedBase58Encoded = "8GXEDszDwrWFqBqTYGaJup"

        with(Id.encode(expectedValue)) {
            assertThat(value, equalTo(expectedValue))
            assertThat(base58Encoded, equalTo(expectedBase58Encoded))
        }
    }

    @Test
    fun testDecode() {
        val expectedValue = "3e6806e9-8605-4438-b843-b929379c48ff".toUUID()
        val expectedBase58Encoded = "8GXEDszDwrWFqBqTYGaJup"

        with(Id.decode(expectedBase58Encoded)) {
            assertThat(value, equalTo(expectedValue))
            assertThat(base58Encoded, equalTo(expectedBase58Encoded))
        }
    }

    @Test
    fun testToString() {
        with(Id.random()) {
            assertThat(toString(), equalTo(base58Encoded))
        }
    }
}

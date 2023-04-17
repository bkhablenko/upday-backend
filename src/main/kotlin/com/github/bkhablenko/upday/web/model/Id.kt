package com.github.bkhablenko.upday.web.model

import com.chrylis.codec.base58.Base58UUID
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode
import com.fasterxml.jackson.annotation.JsonValue
import java.util.UUID
import java.util.UUID.randomUUID

class Id private constructor(val value: UUID, val base58Encoded: String) {

    companion object {
        private val base58 = Base58UUID()

        fun encode(value: UUID): Id {
            val base58Encoded = base58.encode(value)
            return Id(value, base58Encoded)
        }

        @JvmStatic
        @JsonCreator(mode = Mode.DELEGATING)
        fun decode(base58Encoded: String): Id {
            val value = base58.decode(base58Encoded)
            return Id(value, base58Encoded)
        }

        fun random() = encode(randomUUID())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Id

        if (value != other.value) return false
        if (base58Encoded != other.base58Encoded) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + base58Encoded.hashCode()
        return result
    }

    @JsonValue
    override fun toString() = base58Encoded
}

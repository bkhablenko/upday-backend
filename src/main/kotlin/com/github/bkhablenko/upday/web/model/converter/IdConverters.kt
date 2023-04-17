package com.github.bkhablenko.upday.web.model.converter

import com.github.bkhablenko.upday.web.model.Id
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class IdToStringConverter : Converter<Id, String> {

    override fun convert(id: Id) = id.base58Encoded
}

@Component
class StringToIdConverter : Converter<String, Id> {

    override fun convert(base58Encoded: String) = Id.decode(base58Encoded)
}

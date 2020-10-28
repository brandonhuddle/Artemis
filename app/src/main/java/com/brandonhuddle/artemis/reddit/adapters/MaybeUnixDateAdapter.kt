package com.brandonhuddle.artemis.reddit.adapters

import com.brandonhuddle.artemis.reddit.entities.MaybeDate
import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class MaybeUnixDateAdapter : TypeAdapter<MaybeDate>() {
    override fun write(writer: JsonWriter, value: MaybeDate) {
        when (value) {
            is MaybeDate.Boolean -> writer.value(value.value)
            is MaybeDate.Date -> writer.value(value.utcDate.time / 1000)
        }
    }

    override fun read(reader: JsonReader): MaybeDate {
        return when {
            reader.peek() == JsonToken.BOOLEAN -> {
                MaybeDate.Boolean(reader.nextBoolean())
            }
            reader.peek() == JsonToken.NUMBER -> {
                MaybeDate.Date(java.util.Date(reader.nextLong() * 1000))
            }
            else -> {
                throw JsonParseException("unsupported `MaybeDate` value!")
            }
        }
    }
}
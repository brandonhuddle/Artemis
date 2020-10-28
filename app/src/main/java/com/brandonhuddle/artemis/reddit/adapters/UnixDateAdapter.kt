package com.brandonhuddle.artemis.reddit.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

class UnixDateAdapter : TypeAdapter<Date>() {
    override fun write(writer: JsonWriter, value: Date) {
        writer.value(value.time / 1000)
    }

    override fun read(reader: JsonReader): Date {
        return Date(reader.nextLong() * 1000)
    }
}
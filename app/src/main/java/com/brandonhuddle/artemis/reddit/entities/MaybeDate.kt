package com.brandonhuddle.artemis.reddit.entities

// One thing I miss from Swift and Rust: `enum` vs `enum class`
sealed class MaybeDate {
    class Boolean(val value: kotlin.Boolean) : MaybeDate()
    class Date(val utcDate: java.util.Date) : MaybeDate()
}
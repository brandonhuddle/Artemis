package com.brandonhuddle.artemis.reddit.entities

// NOTE: This makes the assumption that `Listing` can only contain one type of `Thing`. Reddit's
//       API documentation has the fun aspect of being very poorly explained (i.e. I giving surface
//       level details about calls rather than justifications and how it works. There are even
//       places where the only documentation is "a boolean value" etc.)
//       I hope this assumption is correct.
data class Listing<T>(
    val before: String,
    val after: String,
    val modhash: String,
    val children: ArrayList<Thing<T>>
)
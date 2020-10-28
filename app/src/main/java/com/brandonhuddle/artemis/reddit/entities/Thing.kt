package com.brandonhuddle.artemis.reddit.entities

// NOTE: My assumption is that the thing kinds are:
//        * t1 == comment
//        * t2 == account
//        * t3 == link
//        * t4 == message
//        * t5 == subreddit
//        * t6 == award
//       This is based NOT on any documentation associated to `kind` but on documentation on the
//       unrelated field type `fullname`
// NOTE2: This has to be `open` because 1. Java's (and Kotlin's) generic system is absolute trash
//        and 2. we have deal with Reddit's trash API that requires us to write a custom
//        deserializer for a type that uses this.
open class Thing<T>(
    val id: String,
    val name: String,
    val kind: String,
    val data: T,
)
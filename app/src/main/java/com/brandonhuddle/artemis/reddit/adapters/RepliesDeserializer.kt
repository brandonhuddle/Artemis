package com.brandonhuddle.artemis.reddit.adapters

import com.brandonhuddle.artemis.reddit.entities.Comment
import com.brandonhuddle.artemis.reddit.entities.Listing
import com.brandonhuddle.artemis.reddit.entities.Thing
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Reddit in all their wisdom decided that when there are no more replies to a comment they should
 * return `""`. For an object type. Not `null`, not `{}`, not even `[]` but an empty string. So
 * rather than it be sane in the idea of returning the same type for the same field they decided
 * that returning an entirely different type for a specific field to show that it isn't filled was
 * the best course of action. I hate the Reddit API.
 *
 * Ranting over, this will either parse the actual replies for the `replies` field or return null
 * when anything else occurs. Bandaids for crap backend design.
 */
class RepliesDeserializer : JsonDeserializer<Thing<Listing<Comment>>?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Thing<Listing<Comment>>? {
        return if (json == null || json.isJsonNull ||
            (json.isJsonPrimitive && json.asJsonPrimitive.isString && json.asString.isBlank())) {
            null
        } else {
            context!!.deserialize<Thing<Listing<Comment>>>(json, CommentToken::class.java)
        }
    }
}
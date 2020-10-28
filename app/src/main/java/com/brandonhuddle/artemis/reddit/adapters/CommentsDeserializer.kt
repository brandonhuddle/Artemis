package com.brandonhuddle.artemis.reddit.adapters

import com.brandonhuddle.artemis.reddit.entities.*
import com.google.gson.*
import java.lang.reflect.Type

// Duct tape to cover glaring issue with the Java/Kotlin generic system. `TypeName::class`
// is fine but `TypeName<OtherType>::class` is not... genius.
class SubmissionToken(id: String, name: String, kind: String, data: Listing<Link>)
    : Thing<Listing<Link>>(id, name, kind, data)
class CommentToken(id: String, name: String, kind: String, data: Listing<Comment>)
    : Thing<Listing<Comment>>(id, name, kind, data)

class CommentsDeserializer : JsonDeserializer<CommentsResponse> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CommentsResponse {
        val rawArray = json.asJsonArray

        // NOTE: As far as I can tell index 0 is always the full submission information and index 1
        //       is always the comment section.
        // NOTE2: GSON _also_ doesn't support anonymous types or local classes so we have to get
        //        the super class...
        val submission = context.deserialize<Thing<Listing<Link>>>(
            rawArray[0],
            SubmissionToken::class.java
        )
        val comments = context.deserialize<Thing<Listing<Comment>>>(
            rawArray[1],
            CommentToken::class.java
        )

        return CommentsResponse(submission, comments)
    }
}
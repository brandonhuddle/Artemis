package com.brandonhuddle.artemis.reddit.entities

import com.brandonhuddle.artemis.reddit.adapters.UnixDateAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.*

data class Message(
    // "Created" Members
    @JsonAdapter(UnixDateAdapter::class)
    val created: Date,
    @JsonAdapter(UnixDateAdapter::class)
    val createdUtc: Date,

    val author: String,
    val body: String,
    @SerializedName("body_html")
    val bodyHtml: String,
    val context: String,
    @SerializedName("first_message")
    val firstMessage: String?, // This seems to be a fun field since even the volunteer writing the documentation wrote (wtf) for this field.
    @SerializedName("first_message_name")
    val firstMessageName: String,
    val likes: Boolean?,
    @SerializedName("link_title")
    val linkTitle: String?,
    val name: String,
    val new: Boolean,
    @SerializedName("parent_id")
    val parentId: String?,
    val replies: String,
    val subject: String,
    val subreddit: String,
    @SerializedName("was_comment")
    val wasComment: Boolean,
)
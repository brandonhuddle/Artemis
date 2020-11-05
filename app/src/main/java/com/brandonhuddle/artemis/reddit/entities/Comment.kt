package com.brandonhuddle.artemis.reddit.entities

import com.brandonhuddle.artemis.reddit.adapters.MaybeUnixDateAdapter
import com.brandonhuddle.artemis.reddit.adapters.RepliesDeserializer
import com.brandonhuddle.artemis.reddit.adapters.UnixDateAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class Comment(
    // "Votable" Members
    val ups: Int,
    val downs: Int,
    val likes: Boolean?,

    // "Created" Members
    @JsonAdapter(UnixDateAdapter::class)
    val created: Date,
    @JsonAdapter(UnixDateAdapter::class)
    @SerializedName("created_utc")
    val createdUtc: Date,

    @SerializedName("approved_by")
    val approvedBy: String,
    val author: String,
    @SerializedName("author_flair_css_class")
    val authorFlairCssClass: String,
    @SerializedName("author_flair_text")
    val authorFlairText: String,
    @SerializedName("banned_by")
    val bannedBy: String?,
    val body: String,
    @SerializedName("body_html")
    val bodyHtml: String,
    @JsonAdapter(MaybeUnixDateAdapter::class)
    val edited: MaybeDate,
    val gilded: Int,
    @SerializedName("link_author")
    val linkAuthor: String,
    @SerializedName("link_id")
    val linkId: String,
    @SerializedName("link_title")
    val linkTitle: String,
    @SerializedName("link_url")
    val linkUrl: String,
    @SerializedName("num_reports")
    val numReports: Int,
    @SerializedName("parent_id")
    val parentId: String,
    @JsonAdapter(RepliesDeserializer::class)
    val replies: Thing<Listing<Comment>>?,
    val saved: Boolean,
    val score: Int,
    @SerializedName("score_hidden")
    val scoreHidden: Boolean,
    val subreddit: String,
    @SerializedName("subreddit_id")
    val subredditId: String,
    val distinguished: String,
)
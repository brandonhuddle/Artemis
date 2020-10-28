package com.brandonhuddle.artemis.reddit.entities

import com.brandonhuddle.artemis.reddit.adapters.MaybeUnixDateAdapter
import com.brandonhuddle.artemis.reddit.adapters.UnixDateAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.*

data class Link(
    val likes: Boolean?,

    val id: String,

    // "Created" Members
    @JsonAdapter(UnixDateAdapter::class)
    val created: Date,
    @JsonAdapter(UnixDateAdapter::class)
    val createdUtc: Date,

    val author: String,
    @SerializedName("author_flair_css_class")
    val authorFlairCssClass: String,
    @SerializedName("author_flair_text")
    val authorFlairText: String,
    val clicked: Boolean,
    val domain: String,
    val hidden: Boolean,
    val locked: Boolean,
    //val media: ???,
    //@SerializedName("media_embed")
    //val mediaEmbed: ???,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("over_18")
    val over18: Boolean,
    val permalink: String,
    val saved: Boolean,
    val score: Int,
    @SerializedName("upvote_ratio")
    val upvoteRatio: Float,
    val selftext: String,
    val subreddit: String,
    val thumbnail: String,
    val title: String,
    val url: String,
    @JsonAdapter(MaybeUnixDateAdapter::class)
    val edited: MaybeDate,
    val distinguished: String,
    val stickied: Boolean,

    // Members found not through documentation
    @SerializedName("link_flair_text")
    val linkFlairText: String?,
    @SerializedName("post_hint")
    val hint: String?,
    val preview: PreviewImage,
    @SerializedName("is_video")
    val isVideo: Boolean,
)
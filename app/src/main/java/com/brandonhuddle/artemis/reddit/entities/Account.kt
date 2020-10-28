package com.brandonhuddle.artemis.reddit.entities

import com.brandonhuddle.artemis.reddit.adapters.UnixDateAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.*

data class Account(
    // "Created" Members
    @JsonAdapter(UnixDateAdapter::class)
    val created: Date,
    @JsonAdapter(UnixDateAdapter::class)
    val createdUtc: Date,

    @SerializedName("comment_karma")
    val commentKarma: Int,
    @SerializedName("has_mail")
    val hasMail: Boolean?,
    @SerializedName("has_mod_mail")
    val hasModMail: Boolean?,
    @SerializedName("has_verified_email")
    val hasVerifiedEmail: Boolean?,
    val id: String,
    @SerializedName("inbox_count")
    val inboxCount: Int,
    @SerializedName("is_friend")
    val isFriend: Boolean,
    @SerializedName("is_gold")
    val isGold: Boolean,
    @SerializedName("is_mod")
    val isMod: Boolean,
    @SerializedName("link_karma")
    val linkKarma: Int,
    val modhash: String,
    // I love the comment for this. They have to specify in the _description_
    // that this is `username` and NOT an related field of the exact same name
    // `name`. If only there was a way to make us know this was the `username`
    // without having to explain it... Nah that would be impossible.
    @SerializedName("name")
    val username: String,
    @SerializedName("over_18")
    val over18: Boolean,
)
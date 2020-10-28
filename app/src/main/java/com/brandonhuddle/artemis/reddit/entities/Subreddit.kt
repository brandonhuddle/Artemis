package com.brandonhuddle.artemis.reddit.entities

import com.google.gson.annotations.SerializedName

data class Subreddit(
    @SerializedName("accounts_active")
    val accountsActive: Int,
    @SerializedName("comment_score_hide_mins")
    val commentScoreHideMinutes: Int,
    val description: String,
    @SerializedName("description_html")
    val descriptionHtml: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("header_image")
    val headerImage: String?,
    @SerializedName("header_size")
    val headerSize: ArrayList<Int>?,
    @SerializedName("header_title")
    val headerTitle: String,
    val over18: Boolean,
    @SerializedName("public_description")
    val publicDescription: String,
    @SerializedName("public_traffic")
    val publicTraffic: Boolean,
    val subscribers: Long,
    @SerializedName("submission_type")
    val submissionType: String,
    @SerializedName("submit_link_label")
    val submitLinkLabel: String,
    @SerializedName("submit_text_label")
    val submitTextLabel: String,
    @SerializedName("subreddit_type")
    val subredditType: String,
    val title: String,
    val url: String,
    @SerializedName("user_is_banned")
    val userIsBanned: Boolean,
    @SerializedName("user_is_contributor")
    val userIsContributor: Boolean,
    @SerializedName("user_is_moderator")
    val userIsModerator: Boolean,
    @SerializedName("user_is_subscriber")
    val userIsSubscriber: Boolean,
)
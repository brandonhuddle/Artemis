package com.brandonhuddle.artemis.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Submission(
        val submissionId: String,
        val title: String,
        val author: String,
        val subreddit: String,
        val totalUpvotes: Int,
        val totalComments: Int,
        val upvoteRatio: Float,
        val content: SubmissionContent,
) : Parcelable
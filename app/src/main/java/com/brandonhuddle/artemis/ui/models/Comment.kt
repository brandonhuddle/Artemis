package com.brandonhuddle.artemis.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Comment(
    // I'm not sure of a better way to handle this without switching from `data class` to
    // `open class`. So this is good enough for me.
    val isMoreComment: Boolean,
    val author: String,
    val body: String,
    val upvotesAreHidden: Boolean,
    val totalUpvotes: Int,
    val creationDateUtc: Date,
//    val replies: List<Comment>,
) : Parcelable
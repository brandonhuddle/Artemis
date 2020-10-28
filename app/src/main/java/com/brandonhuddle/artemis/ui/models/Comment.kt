package com.brandonhuddle.artemis.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    val author: String,
    val body: String,
    val totalUpvotes: Int,
) : Parcelable
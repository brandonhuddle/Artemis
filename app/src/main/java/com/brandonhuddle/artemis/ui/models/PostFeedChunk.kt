package com.brandonhuddle.artemis.ui.models

data class PostFeedChunk(
        val before: String?,
        val after: String?,
        val submissions: List<Submission>,
)
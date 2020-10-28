package com.brandonhuddle.artemis.reddit.entities

data class MultiImage(
    val source: ImageInfo,
    val resolutions: List<ImageInfo>
)
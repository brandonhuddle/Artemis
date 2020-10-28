package com.brandonhuddle.artemis.reddit.entities

data class PreviewImage(
    val enabled: Boolean,
    val images: List<MultiImage>
)
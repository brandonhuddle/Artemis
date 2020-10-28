package com.brandonhuddle.artemis.ui.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// A submission could be a few different things. Currently the plan is to support:
//  * Text
//  * Link
//  * Video
//  * Image
//  * GIF (Actual GIF, "GIFV", and "APNG")
// Possible extra support:
//  * Tweet (I'm not sure if Reddit already handles that for us or if we will have to)
// TODO: Do we need to support Imgur and the various GIF websites explicitly or will Imgur et. al.
//       work out of the box?
abstract class SubmissionContent(
        val typeId: Int
) : Parcelable

enum class SubmissionContentType(val typeId: Int) {
        Empty(0),
        Text(1),
        Link(2),
        Image(3),
        Video(4),
}

// Some posts don't have a body which cause the app to have annoying padding... So anything without
// a body is now considered a special case of `Empty`.
@Parcelize
class SubmissionEmpty()
        : SubmissionContent(SubmissionContentType.Empty.typeId)

@Parcelize
class SubmissionText(
        val text: String
) : SubmissionContent(SubmissionContentType.Text.typeId)

@Parcelize
class SubmissionLink(
        val linkUrl: String,
        val linkPreviewImageUrl: String,
) : SubmissionContent(SubmissionContentType.Link.typeId)

@Parcelize
class SubmissionImage(
        val imageUrl: String,
        val width: Int,
        val height: Int,
        // TODO: Do we want to use the image previews? The "source" image preview is actually half
        //       the size of the original in some cases with the exact same quality... Could be
        //       good to at least investigate using them for initial load.
        // TODO: I think we DEFINITELY want to use image previews for a compact view if we offer
        //       that. In that case we should either use the thumbnail or one of the smallest
        //       previews.
//        val imagePreviewUrl: String,
) : SubmissionContent(SubmissionContentType.Image.typeId)

@Parcelize
class SubmissionVideo(
        val videoUrl: String,
        val width: Int,
        val height: Int,
        val videoPreviewUrl: String,
) : SubmissionContent(SubmissionContentType.Video.typeId)
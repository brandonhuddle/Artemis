package com.brandonhuddle.artemis.repositories

import android.os.Build
import android.text.Html
import com.brandonhuddle.artemis.reddit.RedditApi
import com.brandonhuddle.artemis.reddit.entities.Link
import com.brandonhuddle.artemis.reddit.entities.Thing
import com.brandonhuddle.artemis.ui.models.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.URLDecoder
import javax.inject.Inject

class RedditRepository @Inject constructor(
    private val redditApi: RedditApi
) {
    fun getHotSubmissions(
        after: String? = null,
        before: String? = null,
        limit: Int? = null,
    ): Observable<List<Submission>> {
        return redditApi.getHotSubmissions(after, before, limit)
            .subscribeOn(Schedulers.io())
            .map { thing ->
                thing.data.children.map { thingLink ->
                    convertLinkToSubmission(thingLink.data)
                }
            }
    }

    private fun convertLinkToSubmission(link: Link): Submission {
        // NOTE: It seems like Reddit detects difference by the _lack_ of a `hint` field... rather
        //       than just doing something sane like including the type the post is all the time...
        val content: SubmissionContent = when (link.hint ?: "") {
            "image" -> SubmissionImage(
                link.url,
                link.preview.images[0].source.width,
                link.preview.images[0].source.height
            )
            // FUN FACT: `is_video` returns false for some videos. Amazing.
            "rich:video", "video", "hosted:video" -> {
                SubmissionVideo(
                    link.url,
                    link.preview.images[0].source.width,
                    link.preview.images[0].source.height,
                    if (Build.VERSION.SDK_INT >= 24) {
                        Html.fromHtml(
                            link.preview.images[0].source.url,
                            Html.FROM_HTML_MODE_LEGACY
                        ).toString()
                    } else {
                        Html.fromHtml(
                            link.preview.images[0].source.url
                        ).toString()
                    }
                )
            }
//            "link" -> SubmissionLink(link.url, "")
            else -> when {
                link.selftext.isBlank() -> SubmissionEmpty()
                else -> SubmissionText(link.selftext)
            }
        }

        return Submission(
            link.id,
            link.name,
            link.title,
            link.author,
            link.subreddit,
            link.score,
            link.numComments,
            link.upvoteRatio,
            content
        )
    }

    fun getSubmissionComments(
        subreddit: String,
        submissionId: String,
        sort: String? = null
    ): Observable<List<Comment>> {
        return redditApi.getSubmissionComments(subreddit, submissionId, sort)
            .subscribeOn(Schedulers.io())
            .map { thing ->
                convertServerCommentsToLocalComments(0, thing.comments.data.children)
            }
    }

    // This just feels very inefficient... But I guess it is running on the background thread in a
    // situation where we shouldn't have to worry too much... but still...
    private fun convertServerCommentsToLocalComments(
        depth: Int,
        serverComments: List<Thing<com.brandonhuddle.artemis.reddit.entities.Comment>>
    ): List<com.brandonhuddle.artemis.ui.models.Comment> =
        serverComments
            .flatMap { comment ->
                listOf(
                    listOf(
                        convertServerCommentToLocalComment(comment)
                    ),
                    if (comment.data.replies != null) {
                        convertServerCommentsToLocalComments(
                            depth + 1,
                            comment.data.replies.data.children
                        )
                    } else {
                        listOf()
                    }
                ).flatten()
            }

    private fun convertServerCommentToLocalComment(
        serverComment: Thing<com.brandonhuddle.artemis.reddit.entities.Comment>
    ): Comment =
        if (serverComment.kind == "more")
            Comment(
                true,
                "more",
                "more",
                true,
                0,
                java.util.Date()
            )
        else
            Comment(
                false,
                serverComment.data.author,
                serverComment.data.body,
                serverComment.data.scoreHidden,
                serverComment.data.score,
                serverComment.data.createdUtc
            )
}
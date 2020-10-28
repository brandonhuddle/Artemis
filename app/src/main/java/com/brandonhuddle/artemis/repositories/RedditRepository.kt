package com.brandonhuddle.artemis.repositories

import android.os.Build
import android.text.Html
import com.brandonhuddle.artemis.reddit.RedditApi
import com.brandonhuddle.artemis.reddit.entities.Link
import com.brandonhuddle.artemis.ui.models.*
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
    ): Single<PostFeedChunk> {
        return redditApi.getHotSubmissions(after, before, limit)
                .map { thing ->
                    PostFeedChunk(
                            thing.data.before,
                            thing.data.after,
                            thing.data.children.map { thingLink ->
                                convertLinkToSubmission(thingLink.data)
                            }
                    )
                }
                .subscribeOn(Schedulers.io())
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
    ): Single<List<Comment>> {
        return redditApi.getSubmissionComments(subreddit, submissionId, sort)
            .map { thing ->
                thing.comments.data.children.map { comment ->
                    if (comment.kind == "more")
                        Comment("more", "more", 0)
                    else
                        Comment(
                            comment.data.author,
                            comment.data.body,
                            comment.data.score
                        )
                }
            }
            .subscribeOn(Schedulers.io())
    }
}
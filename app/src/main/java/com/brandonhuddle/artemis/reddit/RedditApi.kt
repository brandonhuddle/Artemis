package com.brandonhuddle.artemis.reddit

import com.brandonhuddle.artemis.reddit.entities.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

// NOTE: Much of the information I have about the JSON responses for Reddit comes from a four year
//       out of date wiki at https://github.com/reddit-archive/reddit/wiki/JSON
//       Everything else comes from here:
//       https://www.reddit.com/dev/api
//       I've read some people praise Reddit for their "well documented API" and I just want to say
//       if anyone reading this thinks that Reddit's documentation is in any way "good" I feel bad
//       for you. You deserve so much better and much more detailed documentation. `/dev/api`
//       doesn't even describe the responses beyond a surface level of "returns a listing" and the
//       4 year old documentation on GitHub is only marginally better but most likely written by
//       someone who doesn't work at Reddit. It also has amazing documentation such as
//       "probably always false". This doesn't tell me what this field does, why it is "probably
//       always false", or what the results are (because "boolean" doesn't mean `true` or `false`,
//       no sometimes "boolean" means `true`, `false`, `null`. Why that isn't `Maybe Boolean`,
//       `Optional Boolean`, or `Boolean?` I have no clue).
//       My favorite piece of documentation is on the `count` parameter. "A positive integer" wow
//       that helps a lot for understanding what `count` is. I can't see any affect it has, it
//       defaults to `0` and there is already a `limit` field to limit the number of results. So I
//       have absolutely no clue what `count` is and telling me it is "a positive integer" is not
//       documentation.
//       /END RANT
interface RedditApi {
    @GET("/hot.json")
    fun getHotSubmissions(
        @Query("after")
        after: String? = null,
        @Query("before")
        before: String? = null,
        @Query("limit")
        limit: Int? = null,
    ): Single<Thing<Listing<Link>>>

    @GET("/r/{subreddit}/hot.json")
    fun getHotSubmissionsIn(
        @Path("subreddit")
        subreddit: String,
        @Query("after")
        after: String? = null,
        @Query("before")
        before: String? = null,
        @Query("limit")
        limit: Int? = null,
    ): Single<Thing<Listing<Link>>>

    @GET("/r/{subreddit}/comments/{submissionId}.json")
    fun getSubmissionComments(
        @Path("subreddit")
        subreddit: String,
        @Path("submissionId")
        submissionId: String,
        @Query("sort")
        sort: String? = null,
    ): Single<CommentsResponse>
}
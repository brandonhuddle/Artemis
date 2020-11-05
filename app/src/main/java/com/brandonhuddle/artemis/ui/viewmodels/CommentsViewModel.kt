package com.brandonhuddle.artemis.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandonhuddle.artemis.repositories.RedditRepository
import com.brandonhuddle.artemis.ui.models.Comment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class CommentsViewModel @ViewModelInject constructor(
    private val redditRepository: RedditRepository
): ViewModel() {
    private val TAG = "CommentsViewModel"
    private var currentSubreddit: String = ""
    private var currentArticleId: String = ""
    private var comments: MutableLiveData<List<Comment>>? = null
    private val commentsState: MutableLiveData<ViewModelState> =
        MutableLiveData(ViewModelState.Idle)

    fun getComments(subreddit: String, articleId: String): LiveData<List<Comment>> {
        if (comments == null || currentSubreddit != subreddit || currentArticleId != articleId) {
            return loadInitialComments(subreddit, articleId)
        } else {
            return comments!!
        }
    }

    fun getCommentsState(): LiveData<ViewModelState> =
        commentsState

    private fun loadInitialComments(subreddit: String, articleId: String): LiveData<List<Comment>> {
        currentSubreddit = subreddit
        currentArticleId = articleId
        comments = MutableLiveData()

        redditRepository
            .getSubmissionComments(subreddit, articleId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { commentResponse ->
                    comments!!.value = commentResponse
                },
                { error ->
                    Log.e(TAG, "getComments: " + error.message)
                }
            )

        return comments!!
    }
}
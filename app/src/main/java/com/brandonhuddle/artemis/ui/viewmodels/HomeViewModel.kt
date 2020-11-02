package com.brandonhuddle.artemis.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandonhuddle.artemis.repositories.RedditRepository
import com.brandonhuddle.artemis.ui.models.Submission
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class HomeViewModel @ViewModelInject constructor(
    private val redditRepository: RedditRepository
): ViewModel() {
    private val TAG = "HomeViewModel"
    private var feedSubmissions: MutableLiveData<List<Submission>>? = null
    private var feedSubmissionsState: MutableLiveData<ViewModelState> =
        MutableLiveData(ViewModelState.Idle)

    fun getFeedSubmissions(): LiveData<List<Submission>> =
        feedSubmissions ?: loadInitialFeedSubmissions()

    fun getFeedSubmissionsState(): LiveData<ViewModelState> =
        feedSubmissionsState

    fun requestMoreFeedSubmissions() =
        loadFeedSubmissionsAfter(feedSubmissions!!.value!!.last().submissionFullname)

    private fun loadInitialFeedSubmissions(): LiveData<List<Submission>> {
        feedSubmissions = MutableLiveData()

        redditRepository
            .getHotSubmissions()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> feedSubmissions!!.value = result },
                { error -> Log.e(TAG, "getFeedSubmissions: " + error.message) }
            )

        return feedSubmissions!!
    }

    private fun loadFeedSubmissionsAfter(after: String) {
        if (feedSubmissionsState.value == ViewModelState.Loading) {
            // Don't start loading more if we're already loading more data.
            return
        }

        feedSubmissionsState.value = ViewModelState.Loading

        redditRepository
            .getHotSubmissions(after)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    feedSubmissionsState.value = ViewModelState.Idle
                    feedSubmissions!!.value = result
                },
                { error ->
                    feedSubmissionsState.value = ViewModelState.Idle
                    Log.e(TAG, "requestMoreFeedSubmissions: " + error.message)
                }
            )
    }
}
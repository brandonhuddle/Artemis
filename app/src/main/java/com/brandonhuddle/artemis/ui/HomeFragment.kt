package com.brandonhuddle.artemis.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brandonhuddle.artemis.R
import com.brandonhuddle.artemis.adapters.OnSubmissionPreviewClickListener
import com.brandonhuddle.artemis.adapters.SubmissionPreviewAdapter
import com.brandonhuddle.artemis.repositories.RedditRepository
import com.brandonhuddle.artemis.ui.models.Submission
import com.brandonhuddle.historynav.HistoryFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : HistoryFragment(), OnSubmissionPreviewClickListener {
    private val TAG_NAME = "HomeFragment"

    private lateinit var recyclerView: RecyclerView
    @Inject lateinit var redditRepository: RedditRepository
    private lateinit var submissionPreviewAdapter: SubmissionPreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = result?.findViewById(R.id.homeSubmissionPreviewRecycler)!!

        val tmpList = ArrayList<Submission>()

        submissionPreviewAdapter = SubmissionPreviewAdapter(requireContext(), tmpList, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = submissionPreviewAdapter

        fillInitialSubmissions()

        return result
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    private fun fillInitialSubmissions() {
        redditRepository
            .getHotSubmissions()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { postFeed ->
                    for (submission in postFeed.submissions) {
                        submissionPreviewAdapter.list.add(submission)
                    }

                    submissionPreviewAdapter.notifyDataSetChanged()
                },
                { error ->
                    Log.e(TAG_NAME, error.message!!)
                }
            )
    }

    override fun onPreviewClicked(submission: Submission) {
        val commentsFragment = CommentsFragment.newInstance(submission, ArrayList())

        showFragment(commentsFragment)
    }
}
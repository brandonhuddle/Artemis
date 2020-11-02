package com.brandonhuddle.artemis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brandonhuddle.artemis.R
import com.brandonhuddle.artemis.adapters.OnApproachEndOfListListener
import com.brandonhuddle.artemis.adapters.OnSubmissionPreviewClickListener
import com.brandonhuddle.artemis.adapters.SubmissionPreviewAdapter
import com.brandonhuddle.artemis.ui.models.Submission
import com.brandonhuddle.artemis.ui.viewmodels.HomeViewModel
import com.brandonhuddle.historynav.HistoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : HistoryFragment(), OnSubmissionPreviewClickListener,
    OnApproachEndOfListListener {
    private lateinit var recyclerView: RecyclerView
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var submissionPreviewAdapter: SubmissionPreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = result?.findViewById(R.id.homeSubmissionPreviewRecycler)!!

        val tmpList = ArrayList<Submission>()

        submissionPreviewAdapter = SubmissionPreviewAdapter(
            requireContext(),
            tmpList,
            this,
            5,
            this
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = submissionPreviewAdapter

        observeSubmissions()

        return result
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

    private fun observeSubmissions() {
        homeViewModel
            .getFeedSubmissions()
            .observe(viewLifecycleOwner,
                { newSubmissions -> submissionPreviewAdapter.addNewSubmissions(newSubmissions!!) }
            )
    }

    override fun onPreviewClicked(submission: Submission) {
        val commentsFragment = CommentsFragment.newInstance(submission, ArrayList())

        showFragment(commentsFragment)
    }

    override fun onApproachEndOfList() {
        homeViewModel.requestMoreFeedSubmissions()
    }
}
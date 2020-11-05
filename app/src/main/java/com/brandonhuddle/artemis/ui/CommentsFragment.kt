package com.brandonhuddle.artemis.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brandonhuddle.artemis.R
import com.brandonhuddle.artemis.adapters.CommentAdapter
import com.brandonhuddle.artemis.adapters.OnCommentClickListener
import com.brandonhuddle.artemis.repositories.RedditRepository
import com.brandonhuddle.artemis.ui.models.*
import com.brandonhuddle.artemis.ui.utils.formatNumericalCount
import com.brandonhuddle.artemis.ui.viewmodels.CommentsViewModel
import com.brandonhuddle.historynav.HistoryFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

private const val ARG_SUBMISSION = "submission"

@AndroidEntryPoint
class CommentsFragment : HistoryFragment(), OnCommentClickListener {
    private val commentsViewModel: CommentsViewModel by viewModels()

    private lateinit var submission: Submission
    private lateinit var submissionTitle: TextView
    private lateinit var submissionContentText: TextView
    private lateinit var submissionContentImage: ImageView
    private lateinit var submissionAuthor: TextView
    private lateinit var submissionSubreddit: TextView
    private lateinit var submissionTotalUpvotes: TextView

    private lateinit var submissionCommentsList: RecyclerView
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            submission = it.getParcelable(ARG_SUBMISSION)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.fragment_comments, container, false)

        val tmpList = ArrayList<Comment>()
        commentAdapter = CommentAdapter(requireContext(), tmpList, this)
        observeComments()

        submissionTitle = result.findViewById(R.id.submissionTitle)
        submissionContentText = result.findViewById(R.id.submissionContentText)
        submissionContentImage = result.findViewById(R.id.submissionContentImage)
        submissionAuthor = result.findViewById(R.id.submissionAuthor)
        submissionSubreddit = result.findViewById(R.id.submissionSubreddit)
        submissionTotalUpvotes = result.findViewById(R.id.submissionTotalUpvotes)

        submissionCommentsList = result.findViewById(R.id.submissionCommentsList)
        submissionCommentsList.layoutManager = LinearLayoutManager(requireContext())
        submissionCommentsList.isNestedScrollingEnabled = false

        submissionCommentsList.adapter = commentAdapter

        submissionTitle.text = submission.title
        submissionAuthor.text = submission.author
        submissionSubreddit.text = submission.subreddit
        submissionTotalUpvotes.text = formatNumericalCount(submission.totalUpvotes)

        submissionContentText.visibility = View.GONE
        submissionContentImage.visibility = View.GONE

        when (submission.content) {
            is SubmissionText -> {
                submissionContentText.visibility = View.VISIBLE

                submissionContentText.text = (submission.content as SubmissionText).text
            }
            is SubmissionImage -> {
                submissionContentImage.visibility = View.VISIBLE

            }
            is SubmissionVideo -> {

            }
        }

        return result
    }

    private fun observeComments() {
        commentsViewModel
            .getComments(submission.subreddit, submission.articleId)
            .observe(viewLifecycleOwner,
                { comments -> commentAdapter.setComments(comments) })
    }

    companion object {
        @JvmStatic
        fun newInstance(submission: Submission, comments: ArrayList<Comment>) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SUBMISSION, submission)
                }
            }
    }

    override fun onCommentClicked(comment: Comment) {

    }
}
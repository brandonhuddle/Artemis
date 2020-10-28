package com.brandonhuddle.artemis.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.brandonhuddle.artemis.R


class SubmissionPreviewToolbar(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    val subredditName: TextView
    val totalUpvotes: TextView
    val totalComments: TextView

    init {
        inflate(getContext(), R.layout.submission_preview_toolbar, this)

        subredditName = findViewById(R.id.submissionPreviewSubreddit)
        totalUpvotes = findViewById(R.id.submissionPreviewTotalUpvotes)
        totalComments = findViewById(R.id.submissionPreviewTotalComments)
    }

    constructor(context: Context)
            : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?)
            : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, 0)
}
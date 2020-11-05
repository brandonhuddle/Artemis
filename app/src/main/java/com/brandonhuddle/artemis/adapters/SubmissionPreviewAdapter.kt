package com.brandonhuddle.artemis.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brandonhuddle.artemis.R
import com.brandonhuddle.artemis.ui.models.*
import com.brandonhuddle.artemis.ui.utils.formatNumericalCount
import com.squareup.picasso.Picasso

class SubmissionPreviewAdapter(
    private val context: Context,
    private val list: ArrayList<Submission>,
    private val onPreviewClickListener: OnSubmissionPreviewClickListener,
    private val endOfListThreshold: Int,
    private val onApproachEndOfListListener: OnApproachEndOfListListener,
) : RecyclerView.Adapter<PreviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val layoutInflater = LayoutInflater
            .from(context)
            .inflate(R.layout.submission_preview, parent, false)

        return when (viewType) {
            SubmissionContentType.Text.typeId -> PreviewTextViewHolder(layoutInflater)
            //SubmissionContentType.Link.typeId -> TODO()
            SubmissionContentType.Image.typeId -> PreviewImageViewHolder(layoutInflater)
            SubmissionContentType.Video.typeId -> PreviewVideoViewHolder(layoutInflater)
            else -> PreviewViewHolder(layoutInflater)
        }
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        if (position >= itemCount - endOfListThreshold) {
            onApproachEndOfListListener.onApproachEndOfList()
        }

        holder.bind(list[position], context, onPreviewClickListener)
    }

    override fun getItemCount(): Int = list.size
    override fun getItemViewType(position: Int): Int = list[position].content.typeId

    fun addNewSubmissions(newSubmissions: List<Submission>) {
        list.addAll(newSubmissions)
        notifyDataSetChanged()
    }
}

// I apologize for my bad names but I couldn't think of a better name for this.
// This is use for when the list is approaching the final item. The threshold can be customized
// and this also WILL send multiple requests. If you set a threshold of 12 then you can expect
// a minimum of 1 call and a maximum of 12 or more unique calls as we approach the end.
interface OnApproachEndOfListListener {
    fun onApproachEndOfList()
}

interface OnSubmissionPreviewClickListener {
    fun onPreviewClicked(submission: Submission)
}

open class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView = itemView.findViewById(R.id.submissionPreviewTitle)
    var subreddit: TextView = itemView.findViewById(R.id.submissionPreviewSubreddit)
    var totalUpvotes: TextView = itemView.findViewById(R.id.submissionPreviewTotalUpvotes)
    var totalComments: TextView = itemView.findViewById(R.id.submissionPreviewTotalComments)
    var previewText: TextView = itemView.findViewById(R.id.submissionPreviewText)
    var previewImage: ImageView = itemView.findViewById(R.id.submissionPreviewImage)

    @SuppressLint("SetTextI18n")
    open fun bind(submission: Submission, context: Context,
                  onPreviewClickListener: OnSubmissionPreviewClickListener) {
        itemView.setOnClickListener {
            onPreviewClickListener.onPreviewClicked(submission)
        }

        previewText.visibility = View.GONE;
        previewImage.visibility = View.GONE;

        title.text = submission.title
        subreddit.text = "r/" + submission.subreddit
        totalUpvotes.text = formatNumericalCount(submission.totalUpvotes)
        totalComments.text = formatNumericalCount(submission.totalComments)
    }

    protected fun setImage(context: Context, width: Int, height: Int, imageUrl: String) {
        // Picasso can't immediately know what the size for the image is (as it needs to
        // download it) as such I'm doing a really bad hack to calculate what the image size is
        // going to be by using the known image size Reddit gives us and using the screen size.
        // This is a bad hack because it assumes we will never add padding to the left or right
        // sides of the preview image... I don't currently plan to but this could be a fun
        // annoyance in the future :)
        val minHeight =
            (context.resources.displayMetrics.widthPixels.toFloat() / width) * height
        previewImage.layoutParams.height = minHeight.toInt()
        previewImage.requestLayout()

        Picasso
            .get()
            .load(imageUrl)
            .into(previewImage)
    }
}

class PreviewTextViewHolder(itemView: View) : PreviewViewHolder(itemView) {
    override fun bind(submission: Submission, context: Context,
                      onPreviewClickListener: OnSubmissionPreviewClickListener) {
        super.bind(submission, context, onPreviewClickListener)
        previewText.visibility = View.VISIBLE;

        previewText.text = (submission.content as SubmissionText).text
    }
}

class PreviewImageViewHolder(itemView: View) : PreviewViewHolder(itemView) {
    override fun bind(submission: Submission, context: Context,
                      onPreviewClickListener: OnSubmissionPreviewClickListener) {
        super.bind(submission, context, onPreviewClickListener)
        previewImage.visibility = View.VISIBLE;

        val contentImage = submission.content as SubmissionImage
        setImage(context, contentImage.width, contentImage.height, contentImage.imageUrl)
    }
}

class PreviewVideoViewHolder(itemView: View) : PreviewViewHolder(itemView) {
    override fun bind(submission: Submission, context: Context,
                      onPreviewClickListener: OnSubmissionPreviewClickListener) {
        super.bind(submission, context, onPreviewClickListener)
        previewImage.visibility = View.VISIBLE;

        val contentImage = submission.content as SubmissionVideo
        setImage(context, contentImage.width, contentImage.height, contentImage.videoPreviewUrl)
    }
}
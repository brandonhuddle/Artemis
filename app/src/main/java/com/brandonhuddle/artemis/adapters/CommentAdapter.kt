package com.brandonhuddle.artemis.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brandonhuddle.artemis.R
import com.brandonhuddle.artemis.ui.models.Comment
import com.brandonhuddle.artemis.ui.utils.formatNumericalCount
import com.brandonhuddle.artemis.ui.utils.formatRelativeTimeShort
import org.w3c.dom.Text

class CommentAdapter(
    private val context: Context,
    private val list: ArrayList<Comment>,
    private val onCommentClickListener: OnCommentClickListener
) : RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater
            .from(context)
            .inflate(R.layout.comment, parent, false)

        return CommentViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(list[position], context, onCommentClickListener)
    }

    override fun getItemCount(): Int = list.size
    override fun getItemViewType(position: Int): Int = 0

    fun setComments(comments: List<Comment>) {
        list.clear()
        list.addAll(comments)
        notifyDataSetChanged()
    }
}

interface OnCommentClickListener {
    fun onCommentClicked(comment: Comment)
}

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val author: TextView = itemView.findViewById(R.id.commentAuthor)
    private val body: TextView = itemView.findViewById(R.id.commentBody)
    private val totalUpvotes: TextView = itemView.findViewById(R.id.commentUpvoteCount)
    private val creationTime: TextView = itemView.findViewById(R.id.commentCreationTime)

    fun bind(comment: Comment, context: Context, onCommentClickListener: OnCommentClickListener) {
        author.text = comment.author
        body.text = comment.body
        totalUpvotes.text = formatNumericalCount(comment.totalUpvotes)
        creationTime.text = formatRelativeTimeShort(comment.creationDateUtc)

        itemView.setOnClickListener {
            onCommentClickListener.onCommentClicked(comment)
        }
    }
}
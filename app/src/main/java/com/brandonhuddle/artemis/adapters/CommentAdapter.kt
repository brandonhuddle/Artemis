package com.brandonhuddle.artemis.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brandonhuddle.artemis.R
import com.brandonhuddle.artemis.ui.models.Comment

class CommentAdapter(
    private val context: Context,
    var list: ArrayList<Comment>,
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
}

interface OnCommentClickListener {
    fun onCommentClicked(comment: Comment)
}

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var author: TextView = itemView.findViewById(R.id.commentAuthor)
    var body: TextView = itemView.findViewById(R.id.commentBody)
    var totalUpvotes: TextView = itemView.findViewById(R.id.commentUpvoteCount)

    fun bind(comment: Comment, context: Context, onCommentClickListener: OnCommentClickListener) {
        author.text = comment.author
        body.text = comment.body
        totalUpvotes.text = comment.totalUpvotes.toString()

        itemView.setOnClickListener {
            onCommentClickListener.onCommentClicked(comment)
        }
    }
}
package com.bahadir.animelist.presentation.animedetail.commment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.databinding.ItemCommentsBinding
import com.bahadir.animelist.domain.model.detail.CommentsUI

class CommentAdapter(private val comments: List<CommentsUI>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentsUI) {
            with(binding) {
                textComment.text = comment.comment
                textUserName.text = comment.userName
                textTime.text = comment.date
                textLove.text = comment.loveIt.toString()
                imgUserProfile.glideImage(comment.imageUrl)
                when (comment.isSpoiler) {
                    true -> textComment.apply {
                        setIsExpanded(false)
                        setReadMoreText("Spoiler")
                    }

                    false -> textComment.apply { setIsExpanded(true) }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

}
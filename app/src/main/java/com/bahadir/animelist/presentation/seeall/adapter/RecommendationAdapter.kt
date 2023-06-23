package com.bahadir.animelist.presentation.seeall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.databinding.ItemRecommendationAnimeBinding
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.presentation.base.DiffUtilCallback

class RecommendationAdapter(val onClick: (Int) -> Unit) :
    PagingDataAdapter<RecommendationsUI, RecommendationAdapter.ViewHolder>(
        DiffUtilCallback(
            itemsTheSame = { oldItem, newItem -> oldItem == newItem },
            contentsTheSame = { oldItem, newItem -> oldItem.id == newItem.id })
    ) {
    inner class ViewHolder(private val binding: ItemRecommendationAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecommendationsUI) {
            with(binding) {
                imgAnime.glideImage(item.imgUrl)
                textName.text = item.title
                card.setOnClickListener {
                    onClick(item.id)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecommendationAnimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }
}
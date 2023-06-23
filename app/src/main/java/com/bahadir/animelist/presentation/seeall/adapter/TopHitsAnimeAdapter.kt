package com.bahadir.animelist.presentation.seeall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.common.extensions.visible
import com.bahadir.animelist.databinding.ItemTopHitsAnimeBinding

import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.presentation.base.DiffUtilCallback

class TopHitsAnimeAdapter(val onClick: (Int) -> Unit) :
    PagingDataAdapter<AnimeUI, TopHitsAnimeAdapter.ViewHolder>(
        DiffUtilCallback(
            itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            contentsTheSame = { oldItem, newItem -> oldItem == newItem })
    ) {
    inner class ViewHolder(private val binding: ItemTopHitsAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AnimeUI) {
            with(binding) {
                imgAnime.glideImage(item.imgUrl)
                textAnimeName.text = item.title
                textGenres.text = item.genres
                textYear.text = item.year.toString()
                textScore.text = item.score.toString()
                btnAnimeDetail.visible()
                btnAnimeDetail.setOnClickListener {
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
            ItemTopHitsAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}
package com.bahadir.animelist.presentation.seeall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.common.extensions.visible
import com.bahadir.animelist.databinding.ItemTopHitsAnimeBinding
import com.bahadir.animelist.domain.model.home.SeasonNowUI
import com.bahadir.animelist.presentation.base.DiffUtilCallback

class SeasonNowAdapter(val onClick: (String?) -> Unit) :
    PagingDataAdapter<SeasonNowUI, SeasonNowAdapter.ViewHolder>(
        DiffUtilCallback(
            itemsTheSame = { oldItem, newItem -> oldItem == newItem },
            contentsTheSame = { oldItem, newItem -> oldItem.id == newItem.id })
    ) {
    inner class ViewHolder(private val binding: ItemTopHitsAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SeasonNowUI) {
            with(binding) {
                imgAnime.glideImage(item.imgUrl)
                textAnimeName.text = item.title
                textSeason.text = item.season
                textYear.text = item.year.toString()
                textScore.text = item.score.toString()
                textGenres.text = item.genres
                text.visible()
                btnPlay.visible()
                btnPlay.setOnClickListener {
                    onClick(item.trailer)
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
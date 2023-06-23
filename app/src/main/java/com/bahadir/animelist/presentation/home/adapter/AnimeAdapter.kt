package com.bahadir.animelist.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.common.extensions.visible
import com.bahadir.animelist.databinding.ItemTopAnimeBinding
import com.bahadir.animelist.domain.model.home.AnimeUI

class AnimeAdapter(
    private val animeList: List<AnimeUI>,
    private val onClickAction: (Int) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTopAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int = animeList.size
    inner class ViewHolder(private val binding: ItemTopAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeUI) {
            with(binding) {
                tvScore.text = anime.score.toString()
                imgTopAnimeImage.glideImage(anime.imgUrl)
                mcvScore.visible()
                card.setOnClickListener {
                    onClickAction.invoke(anime.id)
                }
                anime.rank.toString().also {
                    tvRank.visible()
                    tvRank.text = it
                }
            }
        }
    }

}
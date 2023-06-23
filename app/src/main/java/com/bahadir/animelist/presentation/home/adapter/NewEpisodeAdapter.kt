package com.bahadir.animelist.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.common.extensions.gone
import com.bahadir.animelist.databinding.ItemTopAnimeBinding
import com.bahadir.animelist.domain.model.home.EpisodePopularUI

class NewEpisodeAdapter(
    private val animeList: List<EpisodePopularUI>,
    private val onClickAction: (Int) -> Unit
) : RecyclerView.Adapter<NewEpisodeAdapter.ViewHolder>() {
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
        fun bind(anime: EpisodePopularUI) {
            with(binding) {
                mcvScore.gone()
                imgTopAnimeImage.glideImage(anime.imgUrl)
                card.setOnClickListener {
                    onClickAction.invoke(anime.id)
                }
            }
        }
    }

}
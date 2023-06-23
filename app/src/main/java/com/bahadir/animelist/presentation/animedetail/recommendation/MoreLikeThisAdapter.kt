package com.bahadir.animelist.presentation.animedetail.recommendation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.databinding.ItemTopAnimeBinding
import com.bahadir.animelist.domain.model.home.RecommendationsUI

class MoreLikeThisAdapter(
    private val anime: List<RecommendationsUI>, val onClickAction: (Int) -> Unit
) : RecyclerView.Adapter<MoreLikeThisAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemTopAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: RecommendationsUI) {
            with(binding) {
                imgTopAnimeImage.glideImage(anime.imgUrl)
                imgTopAnimeImage.setOnClickListener {
                    onClickAction(anime.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTopAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = anime.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(anime[position])
    }

}
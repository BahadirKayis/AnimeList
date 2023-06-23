package com.bahadir.animelist.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.common.extensions.gone
import com.bahadir.animelist.databinding.ItemTopAnimeBinding
import com.bahadir.animelist.domain.model.home.RecommendationsUI


class RecommendationsAdapter(
    private val recommendations: List<RecommendationsUI>,
    private val onClickAction: (Int) -> Unit
) :
    RecyclerView.Adapter<RecommendationsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemTopAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: RecommendationsUI) {
            with(binding) {
                mcvScore.gone()
                imgTopAnimeImage.glideImage(anime.imgUrl)
                imgTopAnimeImage.setOnClickListener {
                    onClickAction(anime.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemTopAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recommendations[position])
    }

    override fun getItemCount(): Int = recommendations.size
}
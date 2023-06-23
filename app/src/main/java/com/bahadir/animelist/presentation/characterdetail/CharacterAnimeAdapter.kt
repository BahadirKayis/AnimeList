package com.bahadir.animelist.presentation.characterdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.databinding.ItemCharacterAnimeBinding
import com.bahadir.animelist.domain.model.home.AnimeUI

class CharacterAnimeAdapter(private val listAnim: List<AnimeUI>, val onClickAction: (Int) -> Unit) :
    RecyclerView.Adapter<CharacterAnimeAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemCharacterAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anim: AnimeUI) {
            with(binding) {
                imgAnime.glideImage(anim.imgUrl)
                imgAnime.setOnClickListener {
                    onClickAction.invoke(anim.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCharacterAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listAnim.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAnim[position])
    }

}



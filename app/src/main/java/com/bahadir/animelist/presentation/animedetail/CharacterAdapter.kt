package com.bahadir.animelist.presentation.animedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.databinding.ItemCharacterAnimeBinding
import com.bahadir.animelist.domain.model.detail.AnimeCharacterUI

class CharacterAdapter(
    private var character: List<AnimeCharacterUI>, val onClickAction: (Int) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(), Filterable {
    inner class ViewHolder(private val binding: ItemCharacterAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: AnimeCharacterUI) = with(binding) {
            imgAnime.glideImage(character.imageUrl)
            imgAnime.setOnClickListener {
                onClickAction(character.malId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCharacterAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterAdapter.ViewHolder, position: Int) {
        holder.bind(character[position])
    }

    override fun getItemCount(): Int = character.size

    fun setData(newData: List<AnimeCharacterUI>) {
        character = newData
        notifyItemRangeChanged(0, character.size)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charString: CharSequence?): FilterResults {
                character = character.filter { it.role == charString }
                return FilterResults().apply {
                    values = character
                }
            }

            override fun publishResults(charString: CharSequence?, results: FilterResults?) {
                val filteredDefinitions = results?.values as? List<*>
                filteredDefinitions?.let { def ->
                    character = def.filterIsInstance<AnimeCharacterUI>()
                    notifyItemRangeChanged(0, character.size)
                }
            }
        }
    }
}
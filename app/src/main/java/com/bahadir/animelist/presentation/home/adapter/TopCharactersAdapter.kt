package com.bahadir.animelist.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.databinding.ItemTopCharactersBinding
import com.bahadir.animelist.domain.model.home.CharactersUI


class TopCharactersAdapter(
    private var characters: List<CharactersUI>,
    private var onClickAction: ((CharactersUI) -> Unit)? = null
) : RecyclerView.Adapter<TopCharactersAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemTopCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharactersUI) {
            with(binding) {
                characterImage.glideImage(item.imageUrl)
                characterName.text = item.name
                cons.setOnClickListener {
                    onClickAction?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            ItemTopCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}

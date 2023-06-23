package com.bahadir.animelist.presentation.characterdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.databinding.ItemCharacterAboutBinding
import com.bahadir.animelist.domain.model.CharacterAbout

class CharacterAboutAdapter(private val about: List<CharacterAbout>) :
    RecyclerView.Adapter<CharacterAboutAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemCharacterAboutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(about: CharacterAbout) {
            binding.apply {
                about.value.let {
                    "${about.key}:".also { textKey.text = it }
                    textValue.text = about.value
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCharacterAboutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = about.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(about[position])
    }

}
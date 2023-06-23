package com.bahadir.animelist.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.databinding.ItemFilteredBinding

class FilteredAdapter(val filter: List<String>) :
    RecyclerView.Adapter<FilteredAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemFilteredBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {

            binding.chFilter.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredAdapter.ViewHolder {
        val binding =
            ItemFilteredBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilteredAdapter.ViewHolder, position: Int) {
        holder.bind(filter[position])
    }

    override fun getItemCount(): Int = filter.size

}
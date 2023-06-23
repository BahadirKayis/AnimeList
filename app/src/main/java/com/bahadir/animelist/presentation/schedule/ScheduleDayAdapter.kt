package com.bahadir.animelist.presentation.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.data.model.schedule.ScheduleDay
import com.bahadir.animelist.databinding.ItemScheduleDaysBinding


class ScheduleDayAdapter(private val days: List<ScheduleDay>, val onClick: (String) -> Unit) :
    RecyclerView.Adapter<ScheduleDayAdapter.ViewHolder>() {
    private var selectedPosition = 0

    inner class ViewHolder(private val binding: ItemScheduleDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleDay) {
            binding.apply {
                radio.isChecked = selectedPosition == layoutPosition
                radio.text = item.day

                radio.setOnClickListener {
                    onClick(item.day.lowercase())
                    selectedPosition = layoutPosition
                    notifyItemRangeChanged(0, days.size)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemScheduleDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(days[position])
    }
}
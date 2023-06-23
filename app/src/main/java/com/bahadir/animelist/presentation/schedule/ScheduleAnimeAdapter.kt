package com.bahadir.animelist.presentation.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.glideImage
import com.bahadir.animelist.common.extensions.gone
import com.bahadir.animelist.common.extensions.visible
import com.bahadir.animelist.databinding.ItemScheduleAnimeBinding
import com.bahadir.animelist.domain.model.ScheduleUI
import com.bahadir.animelist.presentation.base.DiffUtilCallback
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class ScheduleAnimeAdapter(val onClick: (String?) -> Unit) :
    PagingDataAdapter<ScheduleUI, ScheduleAnimeAdapter.ViewHolder>(
        DiffUtilCallback(itemsTheSame = { oldItem, newItem -> oldItem.title == newItem.title },
            contentsTheSame = { oldItem, newItem -> oldItem == newItem })
    ) {
    private var currentPosition = -1

    inner class ViewHolder(private val binding: ItemScheduleAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScheduleUI) {
            with(binding) {
                item.time?.let {
                    if (currentPosition == layoutPosition || getCalculationTime(it)) {
                        currentPosition = layoutPosition
                        val dateNow = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                        linearCurrentTime.visible()
                        textCurrentTime.text =
                            root.context.getString(R.string.current_time, dateNow)
                    } else {
                        linearCurrentTime.gone()
                    }
                }
                textTime.text = item.time
                textTitle.text = item.title
                textEpisode.text = root.context.getString(R.string.episodes, item.episode)

                item.trailerImg?.let {
                    imgAnime.glideImage(it)
                } ?: run {
                    imgAnime.glideImage(item.imgUrl)
                }

                imgAnime.setOnClickListener {
                    onClick(item.trailer)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemScheduleAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleAnimeAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    private fun getCalculationTime(time: String): Boolean {
        if (currentPosition == -1) {
            val calendar = Calendar.getInstance()
            val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val itemHour = time.substring(0, 2).toInt()
            val itemMinute = time.substring(3, 5).toInt()
            if (itemHour > hourOfDay || (hourOfDay <= itemHour && minute <= itemMinute)) {
                return true
            }
        }
        return false
    }
}
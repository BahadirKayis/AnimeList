package com.bahadir.animelist.presentation.schedule

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.common.extensions.snackBar
import com.bahadir.animelist.data.repository.local.LocalData
import com.bahadir.animelist.databinding.FragmentScheduleBinding
import com.bahadir.animelist.delegation.viewBinding
import com.bahadir.animelist.presentation.base.PagingStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : Fragment(R.layout.fragment_schedule) {
    private val binding by viewBinding(FragmentScheduleBinding::bind)
    private val viewModel: ScheduleVM by viewModels()
    private val adapterDays by lazy {
        ScheduleDayAdapter(
            LocalData.getScheduleFilterDay(),
            ::dayOnClick
        )
    }
    private val adapter by lazy { ScheduleAnimeAdapter(::scheduleClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIEffect()
        initUIState()
        binding.rvDay.setHasFixedSize(true)
        binding.rvDay.adapter = adapterDays
        binding.rvSchedule.setHasFixedSize(true)

    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) { state ->
        with(binding) {
            progress.isVisible = state.isLoading
            state.anime?.let {
                binding.rvSchedule.adapter =
                    adapter.withLoadStateFooter(PagingStateAdapter { adapter.retry() })
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) { effect ->
        when (effect) {
            is ScheduleUIEffect.SnackBarMessage -> {
                requireView().snackBar(effect.message)
            }

            is ScheduleUIEffect.ActionPlayVideo -> findNavController().navigate(
                ScheduleFragmentDirections.actionScheduleFragmentToVideoPlayerFragment(effect.url)
            )
        }
    }

    private fun scheduleClick(trailer: String?) {
        trailer?.let {
            viewModel.setEvent(ScheduleUIEvent.ActionPlayVideo(it))
        } ?: run {
            viewModel.setEffect(ScheduleUIEffect.SnackBarMessage("Trailer not found"))
        }
    }

    private fun dayOnClick(day: String) {
        viewModel.setEvent(ScheduleUIEvent.GetScheduleAnime(day))
    }
}
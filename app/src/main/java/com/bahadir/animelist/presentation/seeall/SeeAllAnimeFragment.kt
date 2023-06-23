package com.bahadir.animelist.presentation.seeall

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.common.extensions.snackBar
import com.bahadir.animelist.databinding.FragmentSeeAllAnimeBinding
import com.bahadir.animelist.delegation.viewBinding
import com.bahadir.animelist.presentation.base.PagingStateAdapter
import com.bahadir.animelist.presentation.seeall.adapter.RecommendationAdapter
import com.bahadir.animelist.presentation.seeall.adapter.SeasonNowAdapter
import com.bahadir.animelist.presentation.seeall.adapter.TopHitsAnimeAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeeAllAnimeFragment : Fragment(R.layout.fragment_see_all_anime) {
    private val binding by viewBinding(FragmentSeeAllAnimeBinding::bind)
    private val viewModel: SeeAllAnimeVM by viewModels()
    private val adapterTop by lazy { TopHitsAnimeAdapter(::onClickDetail) }
    private val adapterRecommendation by lazy { RecommendationAdapter(::onClickDetail) }
    private val adapterSeasonNow by lazy { SeasonNowAdapter(::onClickTrailer) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIState()
        initUIEvent()
        initUIEffect()
        binding.rvAnime.setHasFixedSize(true)
    }

    private fun initUIEvent() {
        binding.toolbar.setStartIconOnclick {
            viewModel.setEvent(SeeAllAnimeUIEvent.BackPressed)
        }
    }

    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) { effect ->
        when (effect) {
            is SeeAllAnimeUIEffect.BackPressed -> {
                findNavController().popBackStack()
            }

            is SeeAllAnimeUIEffect.ActionAnimeDetail -> {
                findNavController().navigate(
                    SeeAllAnimeFragmentDirections.actionAnimeDetailFragment(
                        effect.id
                    )
                )
            }

            is SeeAllAnimeUIEffect.ActionTrailer -> findNavController().navigate(
                SeeAllAnimeFragmentDirections.actionVideoPlayer(
                    effect.trailerId
                )
            )

            is SeeAllAnimeUIEffect.SnackBarMessage -> requireView().snackBar(effect.message)
        }
    }

    private fun onClickDetail(id: Int) {
        viewModel.setEvent(SeeAllAnimeUIEvent.ActionAnimeDetailUI(id))
    }

    private fun onClickTrailer(trailerId: String?) {
        viewModel.setEvent(SeeAllAnimeUIEvent.ActionTrailer(trailerId))
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) { state ->
        with(binding) {
            progress.isVisible = state.isLoading
            toolbar.isVisible = !state.isLoading

            state.topAnime?.let { anime ->
                toolbar.setTitle("Top Hits Anime")

                rvAnime.adapter =
                    adapterTop.withLoadStateFooter(footer = PagingStateAdapter { adapterTop.retry() })
                adapterTop.submitData(lifecycle, anime)

            }

            state.recommendation?.let { anime ->
                toolbar.setTitle("Recommendation Anime")
                rvAnime.adapter =
                    adapterRecommendation.withLoadStateFooter(footer = PagingStateAdapter {
                        adapterRecommendation.retry()
                    })
                adapterRecommendation.submitData(lifecycle, anime)
                rvAnime.layoutManager = GridLayoutManager(requireContext(), 2).also {
                    it.orientation = GridLayoutManager.VERTICAL
                }
            }

            state.seasonNow?.let { anime ->
                toolbar.setTitle("Season Now Anime")
                rvAnime.adapter =
                    adapterSeasonNow.withLoadStateFooter(footer = PagingStateAdapter { adapterSeasonNow.retry() })
                adapterSeasonNow.submitData(lifecycle, anime)

            }
        }
    }
}
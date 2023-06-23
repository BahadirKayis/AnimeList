package com.bahadir.animelist.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.animelist.R
import com.bahadir.animelist.common.SeeAllAnime
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.common.extensions.snackBar
import com.bahadir.animelist.databinding.FragmentHomeBinding
import com.bahadir.animelist.delegation.viewBinding
import com.bahadir.animelist.domain.model.home.CharactersUI
import com.bahadir.animelist.presentation.home.adapter.AnimeAdapter
import com.bahadir.animelist.presentation.home.adapter.NewEpisodeAdapter
import com.bahadir.animelist.presentation.home.adapter.RecommendationsAdapter
import com.bahadir.animelist.presentation.home.adapter.SeasonNowAdapter
import com.bahadir.animelist.presentation.home.adapter.TopCharactersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIEffect()
        initUIState()
        initUIEvent()
    }


    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) { effect ->
        when (effect) {
            is HomeUIEffect.ActionCharacterDetail -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToCharacterDetail(effect.characters)
                )
            }

            is HomeUIEffect.ActionAnimeDetail -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeToAnimeDetail(effect.id))
            }

            is HomeUIEffect.ActionTopAnime -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionSeeAllFragment(SeeAllAnime.TOP_ANIME)
                )
            }

            is HomeUIEffect.ActionSeasonNow -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionSeeAllFragment(SeeAllAnime.SEASON_NOW)
                )
            }

            is HomeUIEffect.ActionRecommendation -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionSeeAllFragment(SeeAllAnime.RECOMMENDATION)
                )
            }

            is HomeUIEffect.SnackBarMessage ->
                requireView().snackBar(effect.message)
        }
    }

    private fun initUIEvent() {
        with(binding) {
            with(viewModel) {
                includeRc.textTopAnime.setOnClickListener {
                    setEvent(HomeUIEvent.ActionTopAnime)
                }
                includeRc.textSeasonNow.setOnClickListener {
                    setEvent(HomeUIEvent.ActionSeasonNow)
                }
                includeRc.textRecommendation.setOnClickListener {
                    setEvent(HomeUIEvent.ActionRecommendation)
                }
            }
        }
    }

    private fun onClickCharacter(character: CharactersUI) {
        viewModel.setEvent(HomeUIEvent.ActionCharacterDetail(character))
    }

    private fun onClickAnimeId(id: Int) {
        viewModel.setEvent(HomeUIEvent.ActionAnimeDetail(id))
    }

    private fun onClickEpisodes(id: Int) {
        viewModel.setEvent(HomeUIEvent.NewEpisode(getString(R.string.episode_error)))
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) { state ->
        binding.includeShimmer.shimmerLayout.isVisible = state.isLoading
        with(binding.includeRc) {
            state.characters?.let {
                binding.imgSlider.setImageListWithAdapter(
                    TopCharactersAdapter(it, ::onClickCharacter), it.size
                )
            }
            state.episodes?.let {
                rvNewEpisodeReleases.adapter = NewEpisodeAdapter(it, ::onClickEpisodes)
            }
            state.recommendations?.let {
                rvRecommendations.adapter = RecommendationsAdapter(it, ::onClickAnimeId)
            }
            state.anime?.let {
                rvTopAnime.adapter = AnimeAdapter(it, ::onClickAnimeId)
            }
            state.season?.let {
                rvSeasonNow.adapter = SeasonNowAdapter(it, ::onClickAnimeId)
            }
        }
    }

}

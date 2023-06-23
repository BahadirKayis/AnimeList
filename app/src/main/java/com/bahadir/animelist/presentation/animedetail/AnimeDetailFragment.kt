package com.bahadir.animelist.presentation.animedetail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.common.extensions.snackBar
import com.bahadir.animelist.databinding.FragmentAnimeDetailBinding
import com.bahadir.animelist.delegation.viewBinding
import com.bahadir.animelist.domain.model.AnimeDetailUI
import com.bahadir.animelist.domain.model.detail.AnimeCharacterUI
import com.bahadir.animelist.domain.model.detail.CommentsUI
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.presentation.animedetail.commment.CommentsFragment
import com.bahadir.animelist.presentation.animedetail.recommendation.MoreLikeThisFragment
import com.bahadir.animelist.presentation.base.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnimeDetailFragment : Fragment(R.layout.fragment_anime_detail) {
    private val binding by viewBinding(FragmentAnimeDetailBinding::bind)
    private val viewModel: ADetailVM by viewModels()
    private lateinit var adapterCharacter: CharacterAdapter
    private lateinit var character: List<AnimeCharacterUI>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIState()
        initUIEvent()
        initUIEffect()
    }

    private fun initUIEvent() {
        with(binding) {
            with(viewModel) {
                spinnerRole.setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
                    setEvent(ADetailUIEvent.CharacterFilter(newItem))
                }
                btnPlay.setOnClickListener {
                    setEvent(ADetailUIEvent.ActionPlayVideo)
                }
                toolbar.setStartIconOnclick {
                    viewModel.setEvent(ADetailUIEvent.BackPressed)
                }
                imgWeb.setOnClickListener {
                    viewModel.setEvent(ADetailUIEvent.ActionWeb)

                }
                imgSend.setOnClickListener {
                    viewModel.setEvent(ADetailUIEvent.ActionSend)


                }
            }
        }
    }

    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) { effect ->
        when (effect) {
            is ADetailUIEffect.CharacterFilter -> {
                binding.rvCharacter.adapter = adapterCharacter
                adapterCharacter.setData(character)
                adapterCharacter.filter.filter(effect.filter)
            }

            is ADetailUIEffect.ActionPlayVideo -> {
                findNavController().navigate(
                    AnimeDetailFragmentDirections.actionAnimeDetailToVideoPlayer(
                        effect.videoId
                    )
                )
            }

            is ADetailUIEffect.Error -> {
                requireView().snackBar(effect.message)

            }

            is ADetailUIEffect.ActionCharacterDetail -> {
                findNavController().navigate(
                    AnimeDetailFragmentDirections.actionCharacterDetail(
                        effect.character
                    )
                )
            }

            ADetailUIEffect.BackPressed -> findNavController().popBackStack()
            is ADetailUIEffect.ActionSend -> {

            }

            is ADetailUIEffect.ActionWeb -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(effect.url))
                startActivity(browserIntent)
            }
        }
    }


    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) { state ->
        with(binding) {
            progress.isVisible = state.isLoading

            state.animData?.let {
                setData(it)
            }

            state.character?.let {
                character = it
                adapterCharacter = CharacterAdapter(it, ::onClickCharacter)
                rvCharacter.adapter = adapterCharacter

                spinnerRole.apply {
                    setIsFocusable(true)
                    selectItemByIndex(0)

                }
            }

            state.images?.let {
                imgSlider.setImageList(it)
            }

            state.recommendation?.let { recommendation ->
                state.comments?.let { comment ->
                    viewPagerAdapter(recommendation, comment)
                }
            }

        }
    }

    private fun onClickCharacter(id: Int) {
        viewModel.setEvent(ADetailUIEvent.CharacterDetailUI(id))
    }

    private fun setData(data: AnimeDetailUI) {
        with(binding) {
            Log.e("TAG", "updateView: $data")
            textAnimeName.text = data.title
            textYear.text = data.year
            textType.text = data.type
            textScore.text = data.score
            textSynopsis.text = data.synopsis
            textGenre.text = data.genres

        }
    }

    private fun viewPagerAdapter(
        recommendations: List<RecommendationsUI>,
        comments: List<CommentsUI>
    ) {
        with(binding) {
            viewPager.adapter = ViewPagerAdapter(
                childFragmentManager, lifecycle, listOf(
                    MoreLikeThisFragment.newInstance(recommendations),
                    CommentsFragment.newInstance(comments)
                )
            )
            val myArray = resources.getStringArray(R.array.anime_detail_tab)
            myArray[1] = String.format(myArray[1], comments.size)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = myArray[position].toString()
            }.attach()
        }
    }
}

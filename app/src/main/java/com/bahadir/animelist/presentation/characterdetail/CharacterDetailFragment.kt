package com.bahadir.animelist.presentation.characterdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.common.extensions.setDecoder
import com.bahadir.animelist.common.extensions.snackBar
import com.bahadir.animelist.databinding.FragmentCharacterDetailBinding
import com.bahadir.animelist.delegation.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {
    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)
    private val viewModel: ChDetailVM by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().setDecoder(false)
        super.onViewCreated(view, savedInstanceState)
        initEvent()
        initUIState()
        collectEffect()
    }

    private fun initEvent() {
        with(viewModel) {
            with(binding) {
                toolbar.setStartIconOnclick {
                    setEvent(ChDetailUIEvent.BackPressed)
                }
            }
        }
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) { state ->
        with(binding) {
            progress.isVisible = state.isLoading

            state.photoUrl?.let {
                imgSlider.setImageList(it)
            }
            state.character?.let {
                textName.text = it.name
            }
            state.anime?.let {
                rvAnime.adapter = CharacterAnimeAdapter(it) { id ->
                    viewModel.setEvent(ChDetailUIEvent.ActionAnimeDetailUI(id))
                }
            }
            state.about?.let {
                rvAbout.adapter = CharacterAboutAdapter(it)
            }
        }
    }


    private fun collectEffect() = viewModel.effect.collectIn(viewLifecycleOwner) { effect ->
        when (effect) {
            is ChDetailUIEffect.GoBack -> {
                findNavController().popBackStack()
            }

            is ChDetailUIEffect.ActionWebPage -> {
                startActivity(Intent(Intent.ACTION_VIEW, effect.url))
            }

            is ChDetailUIEffect.ActionAnimeDetail -> {
                findNavController().navigate(
                    CharacterDetailFragmentDirections.actionCharacterToAnimeDetail(
                        effect.id
                    )
                )
            }

            is ChDetailUIEffect.Error -> {
                requireView().snackBar(effect.message)
            }
        }
    }
}

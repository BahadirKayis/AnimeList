package com.bahadir.animelist.presentation.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.common.extensions.gone
import com.bahadir.animelist.common.extensions.hideKeyboard
import com.bahadir.animelist.common.extensions.snackBar
import com.bahadir.animelist.databinding.FragmentSearchBinding
import com.bahadir.animelist.delegation.viewBinding
import com.bahadir.animelist.presentation.base.PagingStateAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchVM by viewModels()
    private val adapter by lazy { SearchAdapter(::onClick) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIState()
        initUIEffect()
        initUIEvent()
        binding.rcSearch.setHasFixedSize(true)
        binding.rcSearch.adapter =
            adapter.withLoadStateFooter(footer = PagingStateAdapter { adapter.retry() })


        view.setOnClickListener {
            requireView().hideKeyboard()
            binding.etSearch.focusClear()
        }
    }

    private fun onClick(id: Int) {
        viewModel.setEvent(SearchUIEvent.ActionAnimeDetail(id))
    }

    private fun initUIEvent() {
        binding.btnFilter.setOnClickListener {
            viewModel.setEvent(SearchUIEvent.ShowFilter)
        }
        binding.etSearch.starIconClickListener {
            viewModel.setEvent(SearchUIEvent.Search(binding.etSearch.getText()))
            binding.text.gone()
        }
    }

    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) { effect ->
        when (effect) {
            is SearchUIEffect.BackPressed -> {
                findNavController().popBackStack()
            }

            is SearchUIEffect.ActionAnimeDetail -> {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchToDetail(effect.id)
                )
            }

            is SearchUIEffect.SnackBarMessage -> {
                requireView().snackBar(effect.message)

            }
        }
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) { state ->
        with(binding) {
            progress.isVisible = state.isLoading
            state.anime?.let { anime ->
                adapter.submitData(lifecycle, anime)
                requireView().hideKeyboard()
            }
        }
    }

}


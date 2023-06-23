package com.bahadir.animelist.presentation.animedetail.recommendation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.parcelableList
import com.bahadir.animelist.databinding.FragmentMoreLikeThisBinding
import com.bahadir.animelist.delegation.viewBinding
import com.bahadir.animelist.domain.model.home.RecommendationsUI
import com.bahadir.animelist.presentation.animedetail.AnimeDetailFragmentDirections


private const val ARG_PARAM1 = "param1"

class MoreLikeThisFragment : Fragment(R.layout.fragment_more_like_this) {
    private val adapterRecommendation by lazy {
        val recommendation = arguments?.parcelableList<RecommendationsUI>(ARG_PARAM1)
            ?: throw IllegalArgumentException("Recommendation cannot be null")
        MoreLikeThisAdapter(recommendation, ::oncClickAction)
    }
    private val binding by viewBinding(FragmentMoreLikeThisBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMoreLikeThis.adapter = adapterRecommendation
    }

    private fun oncClickAction(id: Int) {
        findNavController().navigate(AnimeDetailFragmentDirections.actionAnimeDetailFragmentSelf(id))
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: List<RecommendationsUI>) = MoreLikeThisFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARG_PARAM1, ArrayList(param1))

            }
        }
    }
}


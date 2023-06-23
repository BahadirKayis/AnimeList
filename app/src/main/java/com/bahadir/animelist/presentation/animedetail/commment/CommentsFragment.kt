package com.bahadir.animelist.presentation.animedetail.commment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.parcelableList
import com.bahadir.animelist.databinding.FragmentCommentsBinding
import com.bahadir.animelist.delegation.viewBinding
import com.bahadir.animelist.domain.model.detail.CommentsUI


private const val ARG_PARAM1 = "param1"

class CommentsFragment : Fragment(R.layout.fragment_comments) {
    private val adapter by lazy {
        val recommendation = arguments?.parcelableList<CommentsUI>(ARG_PARAM1)
            ?: throw IllegalArgumentException("Recommendation cannot be null")
        CommentAdapter(recommendation)
    }

    private val binding by viewBinding(FragmentCommentsBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvComment.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: List<CommentsUI>) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM1, ArrayList(param1))
                }
            }
    }

}
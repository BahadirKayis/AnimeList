package com.bahadir.animelist.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bahadir.animelist.databinding.PagingStateViewBinding

class PagingStateAdapter(
    val retryCallback: () -> Unit
) : LoadStateAdapter<PagingStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding =
            PagingStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: PagingStateViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            with(binding) {
                progress.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error
                textError.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                textError.text = (loadState as? LoadState.Error)?.error?.message
                btnRetry.setOnClickListener {
                    retryCallback.invoke()
                }
            }

        }
    }
}

package com.bahadir.animelist.presentation.search


import androidx.paging.PagingData
import com.bahadir.animelist.domain.model.home.AnimeUI
import com.bahadir.animelist.presentation.base.ees.State

data class SearchUIState(
    val isLoading: Boolean = false,
    val anime: PagingData<AnimeUI>? = null,
    val filteredList: List<String>? = null
) : State
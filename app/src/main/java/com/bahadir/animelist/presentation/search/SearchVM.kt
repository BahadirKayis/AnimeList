package com.bahadir.animelist.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.delegation.viewmodel.VMDelegation
import com.bahadir.animelist.delegation.viewmodel.VMDelegationImpl
import com.bahadir.animelist.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val dispatcherIO: CoroutineDispatcher,
) : ViewModel(), VMDelegation<SearchUIEffect, SearchUIEvent, SearchUIState>
by VMDelegationImpl(SearchUIState(true)) {

    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is SearchUIEvent.ActionAnimeDetail -> {
                    setEffect(SearchUIEffect.ActionAnimeDetail(event.id))
                }

                is SearchUIEvent.BackPressed -> {
                    setEffect(SearchUIEffect.BackPressed)
                }

                is SearchUIEvent.ShowFilter -> {
                    setEffect(SearchUIEffect.SnackBarMessage("Filter is not implemented yet"))
                }

                is SearchUIEvent.Search -> {
                    search(event.query)
                }
            }
        }
        search("")
    }

    //API değişmiş önceden "" gönderince hepsi geçerli sayılıyordu.
    //şimdi type ve status için 1 tane seçilebiliyor, türü de belirtmen gerekiyor.
    // O yüzden sadece query var, eski hali Github geçmişinden bulabilirsiniz.
    fun search(searchText: String) = viewModelScope.launch {
        searchUseCase.invoke(searchText).flowOn(dispatcherIO).cachedIn(viewModelScope).collect {
            setState(SearchUIState(anime = it, isLoading = false))
        }
    }
}
package com.bahadir.animelist.presentation.activity

import androidx.lifecycle.ViewModel
import com.bahadir.animelist.domain.usecase.activity.IsCurrentUserExistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val isCurrentUserExistUseCase: IsCurrentUserExistUseCase) :
    ViewModel() {
    var isLogged = true

    fun isCurrentUserExistUseCase(): Flow<Boolean> {

        return isCurrentUserExistUseCase.invoke()
    }

}
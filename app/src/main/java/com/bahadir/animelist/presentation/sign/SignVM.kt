package com.bahadir.animelist.presentation.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.animelist.common.Resource
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.delegation.viewmodel.VMDelegation
import com.bahadir.animelist.delegation.viewmodel.VMDelegationImpl
import com.bahadir.animelist.domain.usecase.sign.GoogleSignUpUseCase
import com.bahadir.animelist.domain.usecase.sign.SignInUseCase
import com.bahadir.animelist.domain.usecase.sign.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignVM @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val googleSignUpUseCase: GoogleSignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val dispatcherIO: CoroutineDispatcher,
) : ViewModel(),
    VMDelegation<SignUIEffect, SignUIEvent, SignUIState> by VMDelegationImpl(SignUIState()) {
    private var signState = SignState.SIGN_IN

    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is SignUIEvent.SignUIClicked -> {
                    when (signState) {
                        SignState.SIGN_IN -> {
                            setState(SignUIState(isLoading = true))
                            signIn(event.email, event.password)
                        }

                        SignState.SIGN_UP -> {
                            setState(SignUIState(isLoading = true))
                            signUpEP(event.email, event.password, event.fullName)
                        }
                    }
                }

                is SignUIEvent.SignUIStateUpClicked -> {
                    signState = SignState.SIGN_UP
                    setEffect(SignUIEffect.SignStateUp)
                }

                is SignUIEvent.SignUIStateInClicked -> {
                    signState = SignState.SIGN_IN
                    setEffect(SignUIEffect.SignStateIn)
                }

                is SignUIEvent.GoogleClicked -> {
                    setState(SignUIState(isLoading = true))
                    signGoogle()
                }

                is SignUIEvent.GoToHomeScreen -> {
                    setEffect(SignUIEffect.GoToHomeScreen)
                }

                is SignUIEvent.HideKeyboard -> {
                    setEffect(SignUIEffect.HideKeyboard)
                }
            }
        }
    }

    private fun signUpEP(email: String, password: String, fullName: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && fullName.isNotEmpty()) {
            signUpUseCase.invoke(email, password).flowOn(dispatcherIO).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.user?.let { _ ->
                            setState(SignUIState())
                            setEffect(SignUIEffect.GoToHomeScreen)
                        } ?: run {
                            setState(SignUIState())
                            setEffect(SignUIEffect.ShowError("User is null"))
                        }
                    }

                    is Resource.Error -> {
                        setState(SignUIState())
                        setEffect(SignUIEffect.ShowError(result.message))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun signGoogle() {
        googleSignUpUseCase.invoke().flowOn(dispatcherIO).onEach {
            when (it) {
                is Resource.Success -> {
                    setEffect(SignUIEffect.GoToGoogleAccountScreen(it.data))
                }

                is Resource.Error -> {
                    setState(SignUIState())
                    setEffect(SignUIEffect.ShowError(it.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        googleSignUpUseCase.invoke(idToken).flowOn(dispatcherIO).onEach {
            when (it) {
                is Resource.Success -> {
                    it.data.user?.let { _ ->
                        setState(SignUIState())
                        setEffect(SignUIEffect.GoToHomeScreen)
                    } ?: run {
                        setState(SignUIState())
                        setEffect(SignUIEffect.ShowError("User is null"))
                    }
                }

                is Resource.Error -> {
                    setState(SignUIState())
                    setEffect(SignUIEffect.ShowError(it.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            signInUseCase.invoke(email, password).flowOn(dispatcherIO).onEach {
                when (it) {
                    is Resource.Success -> {
                        it.data.user?.let { _ ->
                            setState(SignUIState())
                            setEffect(SignUIEffect.GoToHomeScreen)

                        } ?: run {
                            setState(SignUIState())
                            setEffect(SignUIEffect.ShowError("User is null"))

                        }
                    }

                    is Resource.Error -> {
                        setState(SignUIState())
                        setEffect(SignUIEffect.ShowError(it.message))
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    enum class SignState {
        SIGN_UP, SIGN_IN
    }

}

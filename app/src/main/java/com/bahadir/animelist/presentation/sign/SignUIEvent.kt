package com.bahadir.animelist.presentation.sign

import com.bahadir.animelist.presentation.base.ees.Event

sealed class SignUIEvent : Event {
    data class SignUIClicked(val email: String, val password: String, val fullName: String) :
        SignUIEvent()

    object GoogleClicked : SignUIEvent()
    object SignUIStateUpClicked : SignUIEvent()
    object SignUIStateInClicked : SignUIEvent()
    object GoToHomeScreen : SignUIEvent()
    object HideKeyboard : SignUIEvent()

}
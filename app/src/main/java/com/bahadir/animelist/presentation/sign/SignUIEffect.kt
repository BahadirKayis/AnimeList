package com.bahadir.animelist.presentation.sign

import android.content.Intent
import com.bahadir.animelist.presentation.base.ees.Effect

sealed class SignUIEffect : Effect {
    data class GoToGoogleAccountScreen(val intent: Intent) : SignUIEffect()
    object SignStateUp : SignUIEffect()
    object SignStateIn : SignUIEffect()
    class ShowError(val message: String) : SignUIEffect()
    object GoToHomeScreen : SignUIEffect()
    object HideKeyboard : SignUIEffect()

}
package com.bahadir.animelist.presentation.characterdetail

import android.net.Uri
import com.bahadir.animelist.presentation.base.ees.Effect

sealed class ChDetailUIEffect : Effect {
    object GoBack : ChDetailUIEffect()
    data class ActionWebPage(val url: Uri) : ChDetailUIEffect()
    data class Error(val message: String) : ChDetailUIEffect()
    data class ActionAnimeDetail(val id: Int) : ChDetailUIEffect()

}
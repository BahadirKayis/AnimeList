package com.bahadir.animelist.common.extensions

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

fun Activity.setDecoder(boolean: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, boolean)
    val controller = ViewCompat.getWindowInsetsController(window.decorView) ?: return
    controller.isAppearanceLightStatusBars = boolean
}

fun Activity.screenOrientationUnspecified() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}


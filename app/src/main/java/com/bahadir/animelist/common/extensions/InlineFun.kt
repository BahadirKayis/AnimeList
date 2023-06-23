package com.bahadir.animelist.common.extensions

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.parcelableList(key: String): List<T> = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") (getParcelableArrayList(key))
} ?: throw IllegalArgumentException("List cannot be null")
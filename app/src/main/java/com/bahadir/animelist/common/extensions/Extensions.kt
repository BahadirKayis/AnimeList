package com.bahadir.animelist.common.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bahadir.animelist.R
import com.bahadir.animelist.data.model.common.Genre
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*


fun ImageView.glideImage(url: String?) {
    Glide.with(context).load(url).override(500, 500) //1
        .diskCacheStrategy(DiskCacheStrategy.DATA) //6
        .placeholder(context.circularProgressDrawable())
        .error(R.drawable.ic_launcher_background).into(this)
}

fun Context.circularProgressDrawable(): Drawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}

fun List<Genre>?.getGenres(): String {
    val genres = this?.joinToString { genre ->
        genre.name
    } ?: ""

    return "Genres: $genres"
}

fun Resources.colorStateList(@ColorRes color: Int) =
    ResourcesCompat.getColorStateList(this, color, null)

fun Resources.color(@ColorRes color: Int): Int {
    return ResourcesCompat.getColor(this, color, null)
}

@Suppress("DEPRECATION")
fun Window.navigationHide() {
    this.decorView.apply {
        systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        } else {
            (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }
}






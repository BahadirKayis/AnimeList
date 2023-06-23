package com.bahadir.animelist.presentation.base.customcomponentview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.gone
import com.bahadir.animelist.common.extensions.visible
import com.bahadir.animelist.databinding.CustomToolbarBinding


class CustomToolbar @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {
    private val binding =
        CustomToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.CustomToolbar).apply {
            //Layout
            val startIcon = getBoolean(R.styleable.CustomToolbar_cvT_startIcon, true)
            val title = getString(R.styleable.CustomToolbar_cvT_title)
            val endLineView = getBoolean(R.styleable.CustomToolbar_cvT_endLineView, true)
            val startIconChange = getResourceId(
                R.styleable.CustomToolbar_cvT_starIconChange,
                R.drawable.ic_back_black
            )
            // val titleGravity = getInt(R.styleable.CustomToolbar_cvT_title_gravity, 1)
            recycle()
            endLineIcon(endLineView)
            startIcon(startIcon)
            setTitle(title)
            startIconChange(startIconChange)
            //  titleGravity(titleGravity)

        }
    }

    private fun startIcon(show: Boolean) {
        if (show) binding.imgStartIcon.visible() else binding.imgStartIcon.gone()
    }

    private fun endLineIcon(show: Boolean) {
        if (show) binding.view.visible() else binding.view.gone()
    }

    fun setTitle(title: String?) {
        binding.tvTitle.text = title
    }

    private fun startIconChange(icon: Int) {
        binding.imgStartIcon.setImageResource(icon)
    }

    fun setStartIconOnclick(onClickAction: () -> Unit) {
        binding.imgStartIcon.setOnClickListener {
            onClickAction.invoke()
        }
    }

    private fun titleGravity(gravity: Int) {
        val type = when (CustomTitleGravity.fromValue(gravity)) {
            CustomTitleGravity.START -> Gravity.START or Gravity.CENTER_VERTICAL
            CustomTitleGravity.CENTER -> Gravity.CENTER
            CustomTitleGravity.END -> Gravity.END or Gravity.CENTER_VERTICAL

        }

        binding.tvTitle.gravity = type
    }

    enum class CustomTitleGravity(val type: Int) {
        START(0), CENTER(1), END(2);

        companion object {
            fun fromValue(value: Int): CustomTitleGravity {
                return values().first { it.type == value }
            }
        }
    }
}
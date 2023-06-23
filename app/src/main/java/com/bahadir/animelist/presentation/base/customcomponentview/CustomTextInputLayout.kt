package com.bahadir.animelist.presentation.base.customcomponentview

import android.content.Context
import android.content.res.Resources
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.color
import com.bahadir.animelist.common.extensions.colorStateList
import com.bahadir.animelist.common.extensions.isValidEmail
import com.bahadir.animelist.common.extensions.isValidPassword
import com.bahadir.animelist.databinding.CustomViewEdittextBinding
import com.google.android.material.textfield.TextInputLayout


class CustomTextInputLayout @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {
    private val binding =
        CustomViewEdittextBinding.inflate(LayoutInflater.from(context), this, true)
    private val layoutDesign = LayoutCustomTextInput(resources = resources, binding = binding)
    private val errorDesign = ErrorCustomTextInput(binding = binding)

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextInputLayout).apply {
            //Layout
            val startIcon = getResourceId(R.styleable.CustomTextInputLayout_cviL_startIcon, 0)
            val passwordToggle =
                getBoolean(R.styleable.CustomTextInputLayout_cviL_passwordToggle, false)
            val boxStrokeColor = getResourceId(
                R.styleable.CustomTextInputLayout_cviL_boxStrokeColor, R.color.white
            )

            val boxCornerRadius = getDimension(R.styleable.CustomTextInputLayout_cviL_radius, 0f)

            //EditText
            val hintE = getString(R.styleable.CustomTextInputLayout_cviE_hint)
            val inputTypeE = getInt(R.styleable.CustomTextInputLayout_cviE_inputType, 0)
            val textErrorMessage =
                getString(R.styleable.CustomTextInputLayout_cviL_textErrorMessage)
            val startIconTint = getResourceId(
                R.styleable.CustomTextInputLayout_cviL_startIconTint, R.color.icon_gray
            )


            layoutDesign.startIcon(startIcon)
            layoutDesign.passwordToggle(passwordToggle)
            layoutDesign.startIconTint(startIconTint)
            layoutDesign.boxCornerRadius(boxCornerRadius)
            layoutDesign.boxStrokeColor(boxStrokeColor)

            textIsValidControl(inputTypeE, textErrorMessage ?: "")
            inputTypeE(inputTypeE)
            hintE(hintE)

            recycle()

        }
    }


    fun getText(): String {
        return binding.editText.text.toString()
    }

    private fun textIsValidControl(inputType: Int, errorMessage: String) {
        when (CustomEdittextInputType.fromValue(inputType)) {
            CustomEdittextInputType.PASSWORD -> {
                validatePasswordInput(errorMessage)
            }

            CustomEdittextInputType.EMAIL -> {
                validateEmailInput(errorMessage)
            }

            CustomEdittextInputType.SEARCH -> {
                validateSearchInput()
            }

            CustomEdittextInputType.TEXT -> {
                validateTextInput()
            }
        }
    }

    private fun validateTextInput() {
        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                layoutDesign.layoutFocus()
            } else {
                if (binding.editText.text.toString().isNotEmpty()) {
                    errorDesign.errorEnabled(false)
                }
            }
        }
    }

    private fun validateEmailInput(errorMessage: String) {
        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                errorDesign.errorEnabled(false)
                layoutDesign.layoutFocus()
            } else {
                if (!getText().isValidEmail()) {
                    errorDesign.errorEnabled(true, errorMessage)
                } else {
                    errorDesign.errorEnabled(false)
                }
            }
        }
    }

    private fun validatePasswordInput(errorMessage: String) {
        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                errorDesign.errorEnabled(false)
                layoutDesign.layoutFocus()
            } else {
                if (!getText().isValidPassword()) {
                    errorDesign.errorEnabled(true, errorMessage)
                } else {
                    errorDesign.errorEnabled(false)
                }
            }
        }
    }

    private fun validateSearchInput() {
        binding.editText.imeOptions = EditorInfo.IME_ACTION_SEARCH
        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                layoutDesign.layoutFocus()
            } else {
                if (binding.editText.text.toString().isNotEmpty()) {
                    errorDesign.errorEnabled(false)
                }
            }
        }
    }

    fun focusClear() {
        binding.editText.clearFocus()
    }

    fun starIconClickListener(click: () -> Unit) {
        binding.layout.setStartIconOnClickListener { click.invoke() }
    }

    private fun hintE(hint: String?) {
        binding.editText.hint = hint

    }

    private fun inputTypeE(inputType: Int) {
        val type = when (CustomEdittextInputType.fromValue(inputType)) {
            CustomEdittextInputType.SEARCH -> InputType.TYPE_CLASS_TEXT
            CustomEdittextInputType.TEXT -> InputType.TYPE_CLASS_TEXT
            CustomEdittextInputType.PASSWORD -> InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            CustomEdittextInputType.EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        type.apply {
            if (this == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
                binding.editText.transformationMethod = PasswordTransformationMethod.getInstance()

            } else {
                binding.editText.inputType = type
            }
        }
    }

    enum class CustomEdittextInputType(val type: Int) {
        TEXT(0), SEARCH(1), PASSWORD(2), EMAIL(3);

        companion object {
            fun fromValue(value: Int): CustomEdittextInputType {
                return values().first { it.type == value }
            }
        }
    }
}

class ErrorCustomTextInput constructor(
    private val binding: CustomViewEdittextBinding
) {
    fun errorEnabled(isError: Boolean, errorMessage: String = "") {
        with(binding.layout) {
            isErrorEnabled = isError

            if (isError) {
                setStartIconTintList(resources.colorStateList(R.color.red))
                // boxStrokeColor = resources.color(R.color.red)
                boxBackgroundColor = resources.color(R.color.custom_layout_error)
                error = errorMessage
            } else {
                setEndIconTintList(resources.colorStateList(R.color.black))
                binding.layout.setStartIconTintList(resources.colorStateList(R.color.black))
                binding.layout.boxBackgroundColor = resources.color(R.color.text_layout)


            }
        }
    }
//    fun isNotCorrect(errorMessage: String = "Password is not correct") {
//        with(binding.layout) {
//            error = errorMessage
//            // errorEnabled()
//        }
//    }

}

class LayoutCustomTextInput constructor(
    private val binding: CustomViewEdittextBinding, private val resources: Resources
) {
    fun startIconTint(color: Int) {
        binding.layout.setStartIconTintList(resources.colorStateList(color))
    }

    fun boxCornerRadius(boxStrokeColor: Float) {
        binding.layout.setBoxCornerRadii(
            boxStrokeColor, boxStrokeColor, boxStrokeColor, boxStrokeColor
        )
    }

    fun startIcon(startIcon: Int) {
        binding.layout.startIconDrawable = ResourcesCompat.getDrawable(resources, startIcon, null)
    }

    fun passwordToggle(passwordToggle: Boolean) {
        if (passwordToggle) {
            binding.layout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        }
    }

    fun boxStrokeColor(color: Int) {
        binding.layout.boxStrokeColor = resources.color(color)
    }

    fun layoutFocus() {
        with(binding.layout) {
            setStartIconTintList(resources.colorStateList(R.color.green_dark_pastel))
            boxStrokeColor = resources.color(R.color.green_dark_pastel)
            boxBackgroundColor = resources.color(R.color.custom_layout)
            setEndIconTintList(resources.colorStateList(R.color.green_dark_pastel))
        }
    }
}

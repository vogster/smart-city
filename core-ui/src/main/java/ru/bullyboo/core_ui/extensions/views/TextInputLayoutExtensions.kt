package ru.bullyboo.core_ui.extensions.views

import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setError(@StringRes stringId: Int?) {
    error = if (stringId == null) {
        null
    } else {
        context.getString(stringId)
    }
}
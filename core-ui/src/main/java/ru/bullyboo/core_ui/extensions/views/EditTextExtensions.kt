package ru.bullyboo.core_ui.extensions.views

import android.text.TextWatcher
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun EditText.applyMask(mask: String): TextWatcher {
    return MaskedTextChangedListener.installOn(this, mask)
}
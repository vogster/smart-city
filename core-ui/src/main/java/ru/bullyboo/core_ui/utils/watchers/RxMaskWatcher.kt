package ru.bullyboo.core_ui.utils.watchers

import android.text.TextWatcher
import android.widget.EditText
import ru.bullyboo.core_ui.extensions.views.applyMask

class RxMaskWatcher: RxWatcher<String>() {

    private var mask: String? = null
    private var maskWatcher: TextWatcher? = null

    override fun setupEditText(editText: EditText) {
        super.setupEditText(editText)

        mask?.let {
            maskWatcher = editText.applyMask(it)
        }
    }

    override fun convert(text: String): String {
        return text
    }

    fun applyMask(mask: String) {
        if (editTextRef == null) {
            this.mask = mask
        } else {
            editTextRef?.get()?.removeTextChangedListener(maskWatcher)
            maskWatcher = editTextRef?.get()?.applyMask(mask)
        }
    }
}
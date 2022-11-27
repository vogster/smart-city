package ru.bullyboo.core_ui.utils

import android.content.Context
import androidx.annotation.StringRes
import java.io.Serializable

sealed class TextProvider: Serializable {

    abstract fun provide(context: Context): String
}

class StringProvider(
    private val text: String
) : TextProvider() {

    constructor(character: Char) :
        this(character.toString())

    override fun provide(context: Context): String {
        return text
    }
}

class StringResProvider(
    @StringRes private val stringId: Int
) : TextProvider() {

    override fun provide(context: Context): String {
        return context.getString(stringId)
    }
}
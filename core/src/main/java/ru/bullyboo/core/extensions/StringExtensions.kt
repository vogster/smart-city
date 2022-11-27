package ru.bullyboo.core.extensions

import java.util.*

fun String?.equalsIgnoreCase(other: String?): Boolean {
    return equals(other, ignoreCase = true)
}

fun String.uppercaseFirstChar(locale: Locale): String =
    this.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(locale)
        } else {
            it.toString()
        }
    }
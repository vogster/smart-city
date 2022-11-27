package ru.bullyboo.core_ui

import android.content.Context

fun Int.toDp(context: Context): Float {
    return context.resources.displayMetrics.density * this
}
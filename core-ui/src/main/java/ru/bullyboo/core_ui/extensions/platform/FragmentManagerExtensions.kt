package ru.bullyboo.core_ui.extensions.platform

import androidx.fragment.app.FragmentManager

fun FragmentManager.clearBackStack() {
    val count = backStackEntryCount

    for (i in 0 until count) {
        popBackStack()
    }
}
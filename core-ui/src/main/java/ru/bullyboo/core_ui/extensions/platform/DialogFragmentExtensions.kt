package ru.bullyboo.core_ui.extensions.platform

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.show(manager: FragmentManager){
    return show(manager, null)
}
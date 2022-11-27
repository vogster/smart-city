package ru.bullyboo.core_ui.extensions.platform

import android.content.Intent
import java.io.Serializable

inline fun <reified M : Serializable> Intent.getObject(key: String): M {
    return getSerializableExtra(key) as M
}

inline fun <reified M : Serializable> Intent.getNullableObject(key: String): M? {
    return getSerializableExtra(key) as? M
}
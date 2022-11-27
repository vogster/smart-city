package ru.bullyboo.core_ui.extensions.platform

import android.os.Bundle
import java.io.Serializable

const val LIST_POSTFIX = "list"
const val LIST_SIZE_POSTFIX = "list_size"

fun Bundle.getNullableDouble(key: String): Double? {
    return get(key) as? Double
}

fun Bundle.getNullableInt(key: String): Int? {
    return get(key) as? Int
}

inline fun <reified M : Serializable> Bundle.getObject(key: String): M {
    return getSerializable(key) as M
}

inline fun <reified M : Serializable> Bundle.getNullableObject(key: String): M? {
    return getSerializable(key) as? M
}

fun Bundle.putSerializableList(name: String, list: List<Serializable>) {
    putSerializable("${name}_${LIST_SIZE_POSTFIX}", list.size)

    list.forEachIndexed { index, it ->
        putSerializable("${name}_${LIST_POSTFIX}_$index", it)
    }
}

inline fun <reified M : Serializable> Bundle.getSerializableList(name: String): List<M> {
    val size = getInt("${name}_${LIST_SIZE_POSTFIX}")

    val list = ArrayList<M>(size)

    for (i in 0 until size) {
        list.add(getSerializable("${name}_${LIST_POSTFIX}_$i") as M)
    }

    return list.toList()
}
package ru.bullyboo.core.extensions

fun Boolean?.isNullOrFalse(): Boolean {
    return this == null || this == false
}
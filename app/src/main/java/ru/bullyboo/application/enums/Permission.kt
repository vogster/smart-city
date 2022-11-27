package ru.bullyboo.application.enums

import android.Manifest

enum class Permission {
    LOCATION,
    STORAGE,
    CAMERA,
    CONTACTS
}

fun Permission.getPermissionsList(): List<String> {
    return when (this) {
        Permission.LOCATION -> {
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        Permission.STORAGE -> {
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        Permission.CAMERA -> {
            listOf(
                Manifest.permission.CAMERA
            )
        }
        Permission.CONTACTS -> {
            listOf(
                Manifest.permission.READ_CONTACTS
            )
        }
    }
}
package ru.bullyboo.core.utils

import android.util.Log
import ru.bullyboo.core.BuildConfig

private const val RELEASE = "release"

fun Any.log(message: String)  {
    if (BuildConfig.BUILD_TYPE != RELEASE) {
        Log.d(this::class.java.simpleName, message)
    }
}

fun Any.log(obj: Any)  {
    if (BuildConfig.BUILD_TYPE != RELEASE) {
        Log.d(this::class.java.simpleName, obj.toString())
    }
}

fun Any.log(error: Throwable, message: String = "")  {
    if (BuildConfig.BUILD_TYPE != RELEASE) {
        Log.e(this::class.java.simpleName, message, error)
    }
}
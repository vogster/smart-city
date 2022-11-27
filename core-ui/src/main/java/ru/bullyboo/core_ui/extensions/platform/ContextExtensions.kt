package ru.bullyboo.core_ui.extensions.platform

import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.os.Vibrator
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.util.*

fun Context.getCompatColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.getCompatColorStateList(@ColorRes colorId: Int): ColorStateList {
    return AppCompatResources.getColorStateList(this, colorId)
}

fun Context.getCompatFont(@FontRes fontId: Int): Typeface? {
    return ResourcesCompat.getFont(this, fontId)
}

fun Context.getCompatDrawable(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

fun Context.getCountryFlagDrawable(countryCode: String): Int {
    return resources.getIdentifier(
        "ic_flag_${countryCode.toLowerCase(Locale.getDefault())}",
        "drawable",
        packageName
    )
}

fun Context.getLocationManager(): LocationManager {
    return getSystemService(Context.LOCATION_SERVICE) as LocationManager
}

fun Context.getInputMethodManager(): InputMethodManager {
    return getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}

fun Context.getClipboardManager(): ClipboardManager {
    return getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
}

fun Context.getVibrator(): Vibrator {
    return getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
}
package ru.bullyboo.application.extensions

import android.app.DatePickerDialog
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import ru.bullyboo.application.R
import ru.bullyboo.application.ui.base.activities.BaseActivity
import java.util.*

fun BaseActivity.showDateDialog(
    listener: DatePickerDialog.OnDateSetListener,
    calendar: Calendar = Calendar.getInstance(),
    @StyleRes style: Int? = null
){
    if(style != null){
        DatePickerDialog(
            this,
            style,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    } else {
        DatePickerDialog(
            this,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

fun BaseActivity.replaceFragment(
    fragment: Fragment,
    container: Int = R.id.container
) {
    supportFragmentManager.beginTransaction()
        .replace(container, fragment)
        .addToBackStack(null)
        .commit()
}
fun BaseActivity.rootFragment(
    fragment: Fragment,
    container: Int = R.id.container
) {
    supportFragmentManager.beginTransaction()
        .add(container, fragment)
        .commit()
}
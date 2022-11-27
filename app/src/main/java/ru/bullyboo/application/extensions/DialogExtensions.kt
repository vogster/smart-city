package ru.bullyboo.application.extensions

import android.app.DatePickerDialog
import android.widget.EditText
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputLayout
import ru.bullyboo.application.R
import ru.bullyboo.application.ui.base.dialogs.BaseDialog
import ru.bullyboo.application.ui.base.fragments.BaseFragment
import ru.bullyboo.core_ui.utils.watchers.RxDateWatcher
import java.util.*

fun BaseDialog<out ViewBinding>.dateWatcher(
    inputLayout: TextInputLayout,
    editText: EditText,
    onNext: ((String) -> Unit)? = null
) {
    RxDateWatcher().observe(editText)
        .subscribe { onNext?.invoke(it) }
        .disposeOnDialogDestroy()
}

fun BaseDialog<out ViewBinding>.showDateDialog(
    listener: DatePickerDialog.OnDateSetListener,
    calendar: Calendar = Calendar.getInstance(),
    maxDate: Calendar = Calendar.getInstance(),
    @StyleRes style: Int? = null
) {
    val dialog = if (style != null) {
        DatePickerDialog(
            requireContext(),
            style,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    } else {
        DatePickerDialog(
            requireContext(),
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    dialog.datePicker.maxDate = maxDate.timeInMillis
    dialog.show()
}

fun <T : ViewBinding> BaseDialog<T>.replaceFragment(
    fragment: Fragment,
    fragmentManager: FragmentManager = parentFragmentManager,
    container: Int = R.id.container
) {
    fragmentManager.beginTransaction()
        .replace(container, fragment)
        .addToBackStack(null)
        .commit()
}
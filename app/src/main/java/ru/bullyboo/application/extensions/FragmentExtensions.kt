package ru.bullyboo.application.extensions

import android.app.DatePickerDialog
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import ru.bullyboo.application.R
import ru.bullyboo.application.enums.Permission
import ru.bullyboo.application.enums.getPermissionsList
import ru.bullyboo.application.ui.base.dialogs.BaseDialog
import ru.bullyboo.application.ui.base.fragments.BaseFragment
import ru.bullyboo.core.utils.log
import ru.bullyboo.core_ui.utils.listeners.RxOnCheckedChangeListener
import java.util.*

fun <T: ViewBinding> BaseFragment <T>.checkWatcher(view: CompoundButton, onNext: (Boolean) -> Unit) {
    RxOnCheckedChangeListener().observe(view)
        .subscribe({
            onNext.invoke(it)
        }, {
            log(it)
        })
        .disposeOnFragmentDestroy()
}

fun <T: ViewBinding> BaseFragment <T>.checkWatcher(view: RadioGroup, onNext: (Int) -> Unit) {
    RxOnCheckedChangeListener().observe(view)
        .subscribe({
            onNext.invoke(it)
        }, {
            log(it)
        })
        .disposeOnFragmentDestroy()
}

fun <T : ViewBinding> BaseFragment<T>.requestRxPermissions(
    permission: Permission,
    actionGranted: () -> Unit
) {
    requestRxPermissions(
        listOf(permission),
        actionGranted,
        null
    )
}

fun <T : ViewBinding> BaseFragment<T>.requestRxPermissions(
    permission: Permission,
    actionGranted: () -> Unit,
    actionDenied: (() -> Unit)? = null
) {
    requestRxPermissions(
        listOf(permission),
        actionGranted,
        actionDenied
    )
}

fun <T : ViewBinding> BaseFragment<T>.requestRxPermissions(
    permissions: List<Permission>,
    actionGranted: () -> Unit,
    actionDenied: (() -> Unit)? = null
) {

    val list = permissions.map { it.getPermissionsList() }.flatten()

    rxPermission.request(*list.toTypedArray())
        .subscribe({ isGranted ->
            if (isGranted) {
                actionGranted.invoke()
            } else {
                actionDenied?.invoke()
            }
        }, {
            log(it)
        })
        .disposeOnFragmentDestroy()
}

fun <T: ViewBinding> BaseFragment <T>.showDateDialog(
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

fun <T : ViewBinding> BaseFragment<T>.replaceFragment(
    fragment: Fragment,
    fragmentManager: FragmentManager = parentFragmentManager,
    container: Int = R.id.container
) {
    fragmentManager.beginTransaction()
        .replace(container, fragment)
        .addToBackStack(null)
        .commit()
}

fun <T : ViewBinding> BaseFragment<T>.showDialog(
    fragment: BaseDialog<out ViewBinding>,
    fragmentManager: FragmentManager = childFragmentManager
) {
    fragment.show(fragmentManager, null)
}
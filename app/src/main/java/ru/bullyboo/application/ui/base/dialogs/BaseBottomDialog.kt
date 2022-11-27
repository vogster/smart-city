package ru.bullyboo.application.ui.base.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.bullyboo.core_ui.binding.InflateType

abstract class BaseBottomDialog<B : ViewBinding>(
    inflate: InflateType<B>
) : BaseDialog<B>(
    inflate
) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext())
    }
}
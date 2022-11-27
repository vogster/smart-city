package ru.bullyboo.application.ui.general

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import ru.bullyboo.application.R
import ru.bullyboo.application.databinding.DialogLoadingBinding

class LoadingDialog : AppCompatDialogFragment() {

    companion object {

        private const val ANIMATION_DURATION = 500L

        fun newInstance() = LoadingDialog()
    }

    private lateinit var binding: DialogLoadingBinding

    private lateinit var animator: ObjectAnimator

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AppCompatDialog(requireContext(), R.style.AppTheme_Dialog_Loading)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        with(binding) {
            container.alpha = 0f

            container.animate()
                .alpha(0f)
                .alphaBy(1f)
                .setDuration(ANIMATION_DURATION)
                .start()

            animator = ObjectAnimator.ofFloat(progressImage, View.ROTATION, 0f, 360f).apply {
                repeatCount = ObjectAnimator.INFINITE
                interpolator = LinearInterpolator()
            }
            animator.start()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        binding.container.alpha = 0f
        animator.cancel()
    }
}
package ru.bullyboo.core_ui.utils

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment

typealias Action = () -> Unit
typealias ActionState = (KeyBoardObserver.State) -> Unit

class KeyBoardObserver(
    private val view: View
) {

    constructor(
        activity: Activity
    ) : this(
        view = activity.findViewById<View>(android.R.id.content).rootView
    )

    constructor(
        fragment: Fragment
    ) : this(
        view = fragment.view
            ?: throw NullPointerException("You can`t observe keyboard with view equals null")
    )

    enum class State {
        OPEN,
        CLOSE
    }

    private var keyboardListener: KeyBoardListener? = null
    private var viewStateListener: ViewStateListener? = null

    private var state = State.CLOSE

    init {
        attachListener()
    }

    fun subscribeOnOpen(listener: Action): KeyBoardObserver {
        keyboardListener?.onOpen = listener
        return this
    }

    fun subscribeOnClose(listener: Action): KeyBoardObserver {
        keyboardListener?.onClose = listener
        return this
    }

    fun subscribe(listener: ActionState): KeyBoardObserver {
        keyboardListener?.onStateChanged = listener
        return this
    }

    fun restart(): KeyBoardObserver {
        removeListeners()
        attachListener()
        return this
    }

    fun dispose() {
        removeListeners()
    }

    private fun attachListener() {
        view.let {
            keyboardListener = KeyBoardListener()
            it.viewTreeObserver.addOnGlobalLayoutListener(keyboardListener)

            viewStateListener = ViewStateListener()
            it.addOnAttachStateChangeListener(viewStateListener)
        }
    }

    private fun removeListeners() {
        view.let {
            it.viewTreeObserver.removeOnGlobalLayoutListener(keyboardListener)
            it.removeOnAttachStateChangeListener(viewStateListener)
        }

        keyboardListener = null
        viewStateListener = null
    }

    private inner class KeyBoardListener : ViewTreeObserver.OnGlobalLayoutListener {

        var onOpen: Action? = null
        var onClose: Action? = null
        var onStateChanged: ActionState? = null

        private val visibleRect = Rect()

        override fun onGlobalLayout() {
            view.let {
                it.getWindowVisibleDisplayFrame(visibleRect)
                val currentHeight = visibleRect.height()
                val patternHeight = it.resources.displayMetrics.heightPixels

                val localState =
                    if (1 - (currentHeight.toFloat() / patternHeight.toFloat()) > 0.2f) {
                        State.OPEN
                    } else {
                        State.CLOSE
                    }

                if (localState != state) {
                    state = localState

                    onOpen?.invoke()
                    onStateChanged?.invoke(localState)
                }
            }
        }
    }

    private inner class ViewStateListener : View.OnAttachStateChangeListener {

        override fun onViewAttachedToWindow(view: View?) {
            /* nothing */
        }

        override fun onViewDetachedFromWindow(view: View?) {
            removeListeners()
        }

    }
}
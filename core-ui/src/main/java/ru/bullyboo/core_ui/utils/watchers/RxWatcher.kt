package ru.bullyboo.core_ui.utils.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.lang.ref.WeakReference

abstract class RxWatcher<M: Any> : TextWatcher {

    protected var editTextRef: WeakReference<EditText>? = null
    private var inputLayoutRef: WeakReference<TextInputLayout>? = null

    private val subject: PublishSubject<M> = PublishSubject.create()

    abstract fun convert(text: String): M

    open fun setupEditText(editText: EditText) = Unit

    open fun observe(editText: EditText): Observable<M> {
        getInputLayout(editText)?.let {
            inputLayoutRef = WeakReference(it)
        }

        editTextRef = WeakReference(editText)

        setupEditText(editText)

        editText.addTextChangedListener(this)

        return subject.doOnDispose {
            editTextRef?.get()?.removeTextChangedListener(this)
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        /* nothing */
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        /* nothing */
    }

    override fun afterTextChanged(s: Editable?) {
        if (s != null) {
            subject.onNext(convert(s.toString()))
        }

        inputLayoutRef?.get()?.let {
            if(it.error != null){
                it.error = null
            }
        }
    }

    protected fun removeWatcher() {
        editTextRef?.get()?.removeTextChangedListener(this)
    }

    protected fun addWatcher() {
        editTextRef?.get()?.addTextChangedListener(this)
    }

    private fun getInputLayout(it: EditText): TextInputLayout? {
        var parent = it.parent

        while (parent !is TextInputLayout) {
            parent = parent.parent

            if (parent == null) {
                return null
            }
        }

        return parent
    }
}
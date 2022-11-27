package ru.bullyboo.core_ui.utils.listeners

import android.view.View
import android.view.View.OnFocusChangeListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxOnFocusChangedListener : OnFocusChangeListener {

    private var subject = PublishSubject.create<Boolean>()

    fun observe(view: View): Observable<Boolean> {
        subject = PublishSubject.create()
        view.onFocusChangeListener = this
        return subject
    }

    override fun onFocusChange(view: View, b: Boolean) {
        subject.onNext(b)
    }
}
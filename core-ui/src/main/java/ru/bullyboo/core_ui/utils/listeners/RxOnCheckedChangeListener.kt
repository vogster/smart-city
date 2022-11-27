package ru.bullyboo.core_ui.utils.listeners

import android.widget.CompoundButton
import android.widget.RadioGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxOnCheckedChangeListener : CompoundButton.OnCheckedChangeListener,
    RadioGroup.OnCheckedChangeListener {

    private var subjectBoolean = PublishSubject.create<Boolean>()
    private var subjectInt = PublishSubject.create<Int>()

    fun observe(compoundButton: CompoundButton): Observable<Boolean> {
        compoundButton.setOnCheckedChangeListener(this)
        return subjectBoolean
    }

    fun observe(radioGroup: RadioGroup): Observable<Int> {
        radioGroup.setOnCheckedChangeListener(this)
        return subjectInt
    }

    override fun onCheckedChanged(
        buttonView: CompoundButton,
        isChecked: Boolean
    ) {
        subjectBoolean.onNext(isChecked)
    }

    override fun onCheckedChanged(radioGroup: RadioGroup, isChecked: Int) {
        subjectInt.onNext(isChecked)
    }
}
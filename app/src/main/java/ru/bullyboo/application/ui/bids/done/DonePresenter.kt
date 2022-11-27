package ru.bullyboo.application.ui.bids.done

import android.content.Context
import ru.bullyboo.application.ui.base.events.EventWrapper
import ru.bullyboo.application.ui.base.presenters.BaseSimplePresenter
import javax.inject.Inject

class DonePresenter @Inject constructor(
    private val context: Context
): BaseSimplePresenter<DoneView>() {

    override fun onHandle(wrapper: EventWrapper) {
        TODO("Not yet implemented")
    }
}
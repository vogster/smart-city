package ru.bullyboo.application.ui.bids.create

import android.content.Context
import ru.bullyboo.application.ui.base.events.EventWrapper
import ru.bullyboo.application.ui.base.presenters.BaseSimplePresenter
import javax.inject.Inject

class BidsCreatePresenter @Inject constructor(
    private val context: Context
): BaseSimplePresenter<BidsCreateView>() {

    override fun onHandle(wrapper: EventWrapper) {
        TODO("Not yet implemented")
    }
}
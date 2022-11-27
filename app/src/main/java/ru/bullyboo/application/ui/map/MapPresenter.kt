package ru.bullyboo.application.ui.map

import android.content.Context
import moxy.InjectViewState
import ru.bullyboo.application.ui.base.events.EventWrapper
import ru.bullyboo.application.ui.base.presenters.BaseSimplePresenter
import javax.inject.Inject

@InjectViewState
class MapPresenter @Inject constructor(
    private val context: Context
): BaseSimplePresenter<MapView>() {

    override fun onHandle(wrapper: EventWrapper) {
        TODO("Not yet implemented")
    }
}
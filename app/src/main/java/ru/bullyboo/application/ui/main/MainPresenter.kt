package ru.bullyboo.application.ui.main

import android.content.Context
import moxy.InjectViewState
import ru.bullyboo.application.ui.base.events.EventWrapper
import ru.bullyboo.application.ui.base.presenters.BaseSimplePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val context: Context
): BaseSimplePresenter<MainView>() {

    override fun onHandle(wrapper: EventWrapper) {
        TODO("Not yet implemented")
    }
}
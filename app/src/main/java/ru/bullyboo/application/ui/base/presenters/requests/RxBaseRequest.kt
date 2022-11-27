package ru.bullyboo.application.ui.base.presenters.requests

import io.reactivex.disposables.Disposable
import ru.bullyboo.application.ui.base.events.Event

open class RxBaseRequest(
    protected val event: Event,
    private val needShowLoading: Boolean,
    private val actionShowLoading: () -> Unit,
    private val actionHideLoading: () -> Unit,
    protected val actionOnError: (Throwable, Event) -> Unit,
    private val actionAddDisposable: (Disposable) -> Unit,
) {

    protected fun showLoading() {
        if (needShowLoading) {
            actionShowLoading.invoke()
        }
    }

    protected fun hideLoading() {
        if (needShowLoading) {
            actionHideLoading.invoke()
        }
    }

    protected fun Disposable.disposeOnPresenterDestroy(): Disposable {
        actionAddDisposable(this)
        return this
    }
}
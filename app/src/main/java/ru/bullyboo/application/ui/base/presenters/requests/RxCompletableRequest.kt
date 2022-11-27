package ru.bullyboo.application.ui.base.presenters.requests

import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import ru.bullyboo.application.ui.base.events.Event
import ru.bullyboo.core_ui.extensions.rx.schedulerIoToMain

class RxCompletableRequest(
    event: Event,
    needShowLoading: Boolean,
    actionShowLoading: () -> Unit,
    actionHideLoading: () -> Unit,
    actionOnError: (Throwable, Event) -> Unit,
    actionAddDisposable: (Disposable) -> Unit,
    private val completable: Completable
) : RxBaseRequest(
    event,
    needShowLoading,
    actionShowLoading,
    actionHideLoading,
    actionOnError,
    actionAddDisposable
) {

    fun subscribe(
        onSuccess: (() -> Unit)
    ): Disposable {
        return subscribe(onSuccess, null)
    }

    fun subscribe(
        onSuccess: (() -> Unit)?,
        onError: ((Throwable) -> Unit)?
    ): Disposable {
        showLoading()

        return completable.schedulerIoToMain()
            .subscribe({
                hideLoading()
                onSuccess?.invoke()
            }, {
                hideLoading()
                onError?.invoke(it)
                actionOnError.invoke(it, event)
            })
            .disposeOnPresenterDestroy()
    }
}
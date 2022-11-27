package ru.bullyboo.application.ui.base.presenters.requests

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import ru.bullyboo.application.ui.base.events.Event
import ru.bullyboo.core_ui.extensions.rx.schedulerIoToMain

class RxObservableRequest<M>(
    event: Event,
    needShowLoading: Boolean,
    actionShowLoading: () -> Unit,
    actionHideLoading: () -> Unit,
    actionOnError: (Throwable, Event) -> Unit,
    actionAddDisposable: (Disposable) -> Unit,
    private val observable: Observable<M>
) : RxBaseRequest(
    event,
    needShowLoading,
    actionShowLoading,
    actionHideLoading,
    actionOnError,
    actionAddDisposable
) {

    fun subscribe(
        onSuccess: ((M) -> Unit)
    ): Disposable {
        return subscribe(onSuccess, null)
    }

    fun subscribe(
        onSuccess: ((M) -> Unit)?,
        onError: ((Throwable) -> Unit)?
    ): Disposable {
        showLoading()

        return observable.schedulerIoToMain()
            .subscribe({
                hideLoading()
                onSuccess?.invoke(it)
            }, {
                hideLoading()
                onError?.invoke(it)
                actionOnError.invoke(it, event)
            })
            .disposeOnPresenterDestroy()
    }
}
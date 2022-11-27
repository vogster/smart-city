package ru.bullyboo.application.ui.base.presenters.requests

import io.reactivex.Maybe
import io.reactivex.disposables.Disposable
import ru.bullyboo.application.ui.base.events.Event
import ru.bullyboo.core_ui.extensions.rx.schedulerIoToMain

class RxMaybeRequest<M>(
    event: Event,
    needShowLoading: Boolean,
    actionShowLoading: () -> Unit,
    actionHideLoading: () -> Unit,
    actionOnError: (Throwable, Event) -> Unit,
    actionAddDisposable: (Disposable) -> Unit,
    private val maybe: Maybe<M>
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
        return subscribe(onSuccess, onError, null)
    }

    fun subscribe(
        onSuccess: ((M) -> Unit)?,
        onError: ((Throwable) -> Unit)?,
        onComplete: (() -> Unit)?
    ): Disposable {
        showLoading()

        return maybe.schedulerIoToMain()
            .subscribe({
                hideLoading()
                onSuccess?.invoke(it)
            }, {
                hideLoading()
                onError?.invoke(it)
                actionOnError.invoke(it, event)
            }, {
                hideLoading()
                onComplete?.invoke()
            })
            .disposeOnPresenterDestroy()
    }
}
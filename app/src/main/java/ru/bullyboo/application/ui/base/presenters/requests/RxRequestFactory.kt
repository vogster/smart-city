package ru.bullyboo.application.ui.base.presenters.requests

import io.reactivex.*
import io.reactivex.disposables.Disposable
import ru.bullyboo.application.ui.base.events.Event

class RxRequestFactory(
    private val actionShowLoading: () -> Unit,
    private val actionHideLoading: () -> Unit,
    private val actionOnError: (Throwable, Event) -> Unit,
    private val actionAddDisposable: (Disposable) -> Unit,
) {

    fun <M> getRequest(
        event: Event,
        single: Single<M>,
        needShowLoading: Boolean
    ): RxSingleRequest<M> =
        RxSingleRequest(
            event = event,
            needShowLoading = needShowLoading,
            actionShowLoading = actionShowLoading,
            actionHideLoading = actionHideLoading,
            actionOnError = actionOnError,
            actionAddDisposable = actionAddDisposable,
            single = single
        )

    fun <M> getRequest(
        event: Event,
        maybe: Maybe<M>,
        needShowLoading: Boolean
    ): RxMaybeRequest<M> =
        RxMaybeRequest(
            event = event,
            needShowLoading = needShowLoading,
            actionShowLoading = actionShowLoading,
            actionHideLoading = actionHideLoading,
            actionOnError = actionOnError,
            actionAddDisposable = actionAddDisposable,
            maybe = maybe
        )

    fun getRequest(
        event: Event,
        completable: Completable,
        needShowLoading: Boolean
    ): RxCompletableRequest =
        RxCompletableRequest(
            event = event,
            needShowLoading = needShowLoading,
            actionShowLoading = actionShowLoading,
            actionHideLoading = actionHideLoading,
            actionOnError = actionOnError,
            actionAddDisposable = actionAddDisposable,
            completable = completable
        )

    fun <M> getRequest(
        event: Event,
        observable: Observable<M>,
        needShowLoading: Boolean
    ): RxObservableRequest<M> =
        RxObservableRequest(
            event = event,
            needShowLoading = needShowLoading,
            actionShowLoading = actionShowLoading,
            actionHideLoading = actionHideLoading,
            actionOnError = actionOnError,
            actionAddDisposable = actionAddDisposable,
            observable = observable
        )

    fun <M> getRequest(
        event: Event,
        flowable: Flowable<M>,
        needShowLoading: Boolean
    ): RxFlowableRequest<M> =
        RxFlowableRequest(
            event = event,
            needShowLoading = needShowLoading,
            actionShowLoading = actionShowLoading,
            actionHideLoading = actionHideLoading,
            actionOnError = actionOnError,
            actionAddDisposable = actionAddDisposable,
            flowable = flowable
        )
}
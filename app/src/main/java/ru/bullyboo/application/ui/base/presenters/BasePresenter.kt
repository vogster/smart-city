package ru.bullyboo.application.ui.base.presenters

import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import retrofit2.HttpException
import ru.bullyboo.application.ui.base.BaseView
import ru.bullyboo.application.ui.base.events.Event
import ru.bullyboo.application.ui.base.events.EventWrapper
import ru.bullyboo.application.ui.base.presenters.requests.*
import ru.bullyboo.core.utils.log
import ru.bullyboo.domain.interactors.BaseInteractor
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Базовый класс Presenter, реализующий основной функционал, который нужен для
 * правильной работы Presenter
 */
abstract class BasePresenter<V : BaseView>(
    private val interactor: BaseInteractor
) : MvpPresenter<V>() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    private val eventWrapper = EventWrapper()

    private val rxRequestFactory = RxRequestFactory(
        actionShowLoading = { viewState.showLoadingDialog() },
        actionHideLoading = { viewState.hideLoadingDialog() },
        actionOnError = { throwable, event -> onError(throwable, event) },
        actionAddDisposable = { disposable -> disposable.disposeOnPresenterDestroy() }
    )

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    /**
     * Внешний метод для делегирования обработки Event презентеру
     * В данном методе может быть сделана предобработка сосбытия, до его поступления в
     * метод onHandle, такое поведение может быть полезно для абстрактных наследников Presenter
     *
     * @param event - событые, отправленное в презентер для обработки
     */
    open fun post(event: Event) {
        eventWrapper.event = event
        onHandle(eventWrapper)
    }

    open fun post(vararg events: Event) {
        events.forEach {
            eventWrapper.event = it
            onHandle(eventWrapper)
        }
    }

    /**
     * Метод, позволяющий привязать Disposable к жизненному циклу BasePresenter
     * Таким образом происходит автоматическая отписка всех подписок Presenter,
     * при разрушении Presenter
     */
    fun Disposable.disposeOnPresenterDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    /**
     * Обработка событий, которые могут переданы в презентер
     *
     * @param wrapper - обертка над событием, передаваемая наследникам
     */
    protected abstract fun onHandle(wrapper: EventWrapper)

    fun <M> Event.execute(
        single: Single<M>,
        needShowLoading: Boolean = true
    ): RxSingleRequest<M> {
        return rxRequestFactory.getRequest(
            event = this,
            single = single,
            needShowLoading = needShowLoading
        )
    }

    fun <M> Event.execute(
        maybe: Maybe<M>,
        needShowLoading: Boolean = true
    ): RxMaybeRequest<M> {
        return rxRequestFactory.getRequest(
            event = this,
            maybe = maybe,
            needShowLoading = needShowLoading
        )
    }

    fun Event.execute(
        completable: Completable,
        needShowLoading: Boolean = true
    ): RxCompletableRequest {
        return rxRequestFactory.getRequest(
            event = this,
            completable = completable,
            needShowLoading = needShowLoading
        )
    }

    fun <M> Event.execute(
        observable: Observable<M>,
        needShowLoading: Boolean = true
    ): RxObservableRequest<M> {
        return rxRequestFactory.getRequest(
            event = this,
            observable = observable,
            needShowLoading = needShowLoading
        )
    }

    fun <M> Event.execute(
        flowable: Flowable<M>,
        needShowLoading: Boolean = true
    ): RxFlowableRequest<M> {
        return rxRequestFactory.getRequest(
            event = this,
            flowable = flowable,
            needShowLoading = needShowLoading
        )
    }

    /**
     * Метод обработки ошибки по умолчанию
     * По умолчанию используется для подписок на сетевых запросов
     * Реазилация данного метода требует на вход Event, который должен быть повторно исполнен
     * презентером, в случае ошибки
     *
     * @param throwable - ошибка, нарушевшая работу подписки
     * @param event - событие, которые вызвало создание подписки и его последующую ошибку
     */
    private fun onError(throwable: Throwable, event: Event) {
        log(throwable)
        viewState.hideLoadingDialog()

        when (throwable) {
            is UnknownHostException,
            is ConnectException -> viewState.showConnectionError(event)
            is HttpException -> {
                if (throwable.code() == 401) {
                    refreshToken(event)
                }
            }
        }

        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    private fun refreshToken(event: Event) {
        if (interactor.hasTokens()) {
            val request = interactor.refreshAccessToken()

            event.execute(request)
                .subscribe {
                    post(event)
                }
        }
    }
}
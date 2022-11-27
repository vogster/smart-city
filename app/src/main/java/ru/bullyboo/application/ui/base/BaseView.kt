package ru.bullyboo.application.ui.base

import androidx.annotation.StringRes
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.bullyboo.application.ui.base.events.Event

interface BaseView: MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showToast(@StringRes stringId: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showToast(text: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading() = Unit

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showConnectionError(event: Event) = Unit

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showContent() = Unit

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMessageDialog(event: Event) = Unit

    @StateStrategyType(SkipStrategy::class)
    fun showLoadingDialog()

    @StateStrategyType(SkipStrategy::class)
    fun hideLoadingDialog()
}
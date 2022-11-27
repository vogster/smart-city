package ru.bullyboo.application.ui.bids.create

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.bullyboo.application.dagger.Dagger
import ru.bullyboo.application.ui.base.activities.BaseFragmentActivity
import javax.inject.Inject
import javax.inject.Provider


class BidsCreateActivity: BaseFragmentActivity(), BidsCreateView {

    @Inject
    internal lateinit var provider: Provider<BidsCreatePresenter>

    @InjectPresenter
    internal lateinit var presenter: BidsCreatePresenter

    @ProvidePresenter
    fun provide(): BidsCreatePresenter = provider.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        Dagger.app.main.provide().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun provideFragment() = BidsNewCreateFragment.newInstance()
}
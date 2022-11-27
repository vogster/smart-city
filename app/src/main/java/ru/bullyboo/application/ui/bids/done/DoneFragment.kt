package ru.bullyboo.application.ui.bids.done

import android.content.Context
import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.bullyboo.application.dagger.Dagger
import ru.bullyboo.application.databinding.FragmentDoneBinding
import ru.bullyboo.application.ui.base.activities.BaseFragmentActivity
import ru.bullyboo.application.ui.base.fragments.BaseFragment
import ru.bullyboo.application.ui.bids.create.BidsNewCreateFragment
import ru.bullyboo.application.ui.bids.create.BidsNewCreatePresenter
import javax.inject.Inject
import javax.inject.Provider

class DoneFragment : BaseFragment<FragmentDoneBinding>(
    FragmentDoneBinding::inflate
), DoneView  {

    companion object {

        fun newInstance() = DoneFragment()
    }

    @Inject
    internal lateinit var provider: Provider<DonePresenter>

    @InjectPresenter
    internal lateinit var presenter: DonePresenter

    @ProvidePresenter
    fun provide(): DonePresenter = provider.get()

    override fun onAttach(context: Context) {
        Dagger.app.main.provide().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            animationView.playAnimation()
        }
    }
}
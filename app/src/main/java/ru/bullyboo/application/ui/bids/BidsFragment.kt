package ru.bullyboo.application.ui.bids

import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.bullyboo.application.R
import ru.bullyboo.application.dagger.Dagger
import ru.bullyboo.application.databinding.FragmentBidsBinding
import ru.bullyboo.application.ui.base.fragments.BaseFragment
import ru.bullyboo.application.ui.bids.adapter.BidsViewPagerAdapter
import ru.bullyboo.application.ui.bids.create.BidsCreateActivity
import ru.bullyboo.core_ui.extensions.platform.startBackTo
import javax.inject.Inject
import javax.inject.Provider

class BidsFragment : BaseFragment<FragmentBidsBinding>(
    FragmentBidsBinding::inflate
), BidsView {

    companion object {

        val animalsArray = arrayOf(
            "Мои",
            "Все",
        )

        fun newInstance() = BidsFragment()
    }

    @Inject
    internal lateinit var provider: Provider<BidsPresenter>

    @InjectPresenter
    internal lateinit var presenter: BidsPresenter

    @ProvidePresenter
    fun provide(): BidsPresenter = provider.get()

    override fun onAttach(context: Context) {
        Dagger.app.main.provide().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val viewPager = viewPager
            val tabLayout = tabLayout

            val adapter = BidsViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = animalsArray[position]
            }.attach()

            fab.setOnClickListener { view ->
                startBackTo(BidsCreateActivity::class)
            }
        }
    }
}
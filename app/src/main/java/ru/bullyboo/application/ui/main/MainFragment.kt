package ru.bullyboo.application.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_bids_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.bullyboo.application.R
import ru.bullyboo.application.dagger.Dagger
import ru.bullyboo.application.databinding.FragmentMainBinding
import ru.bullyboo.application.ui.base.fragments.BaseFragment
import ru.bullyboo.application.ui.main.adapter.BidsListAdapter
import ru.bullyboo.application.ui.main.adapter.ViewPagerAdapter
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class MainFragment : BaseFragment<FragmentMainBinding>(
    FragmentMainBinding::inflate
), MainView {

    companion object {

        fun newInstance() = MainFragment()
    }

    @Inject
    internal lateinit var provider: Provider<MainPresenter>

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provide(): MainPresenter = provider.get()

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var bidsListAdapter: BidsListAdapter

    override fun onAttach(context: Context) {
        Dagger.app.main.provide().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            pagerAdapter = ViewPagerAdapter()
            bidsListAdapter = BidsListAdapter()

            viewPagerMain.adapter = pagerAdapter
            bidsList.adapter = bidsListAdapter

            Timer().schedule(object : TimerTask() {
                // task to be scheduled
                override fun run() {
                    if (viewPagerMain.currentItem == pagerAdapter.itemCount - 1) {
                        viewPagerMain.currentItem = 0
                    } else {
                        viewPagerMain.currentItem = viewPagerMain.currentItem + 1
                    }

                }
            }, 5000, 5000)

            pagerAdapter.submitList(
                listOf(
                    R.drawable.demo_pic_1,
                    R.drawable.demo_pic_2,
                    R.drawable.demo_pic_3
                )
            )

            bidsListAdapter.submitList(
                listOf(
                    BidsListAdapter.MainBidsList(
                        title = "Ремонт тротуаров",
                        avatar = R.drawable.kira,
                        deadline = "до 4 июня",
                        likes = "25.5 К"
                    ),
                    BidsListAdapter.MainBidsList(
                        title = "Благоустройство парка “Ынтымак”",
                        avatar = R.drawable.kira,
                        deadline = "до 14 мая",
                        likes = "18 К"
                    ),
                    BidsListAdapter.MainBidsList(
                        title = "Улучшение условий в общественном транспорте",
                        avatar = R.drawable.kira,
                        deadline = "до 1 сентября",
                        likes = "15 К"
                    ),
                )
            )
        }
    }
}
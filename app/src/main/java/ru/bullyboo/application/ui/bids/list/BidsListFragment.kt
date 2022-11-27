package ru.bullyboo.application.ui.bids.list

import android.content.Context
import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.bullyboo.application.dagger.Dagger
import ru.bullyboo.application.databinding.FragmentBidsListBinding
import ru.bullyboo.application.ui.base.fragments.BaseFragment
import ru.bullyboo.application.ui.bids.list.adapter.BidsAdapter
import ru.bullyboo.domain.entity.BidsData
import javax.inject.Inject
import javax.inject.Provider

class BidsListFragment: BaseFragment<FragmentBidsListBinding>(
    FragmentBidsListBinding::inflate
), BidsListView {

    companion object {

        fun newInstance() = BidsListFragment()
    }

    @Inject
    internal lateinit var provider: Provider<BidsListPresenter>

    @InjectPresenter
    internal lateinit var presenter: BidsListPresenter

    @ProvidePresenter
    fun provide(): BidsListPresenter = provider.get()

    private val bidsAdapter by lazy {
        BidsAdapter()
    }

    override fun onAttach(context: Context) {
        Dagger.app.main.provide().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            bidsAdapter.submitList(listOf(
                BidsData(
                    id = null,
                    title ="Большая проблема",
                    description = "Очень большая проблема",
                    created_at = null,
                    rating = null,
                    status = null
                ),
                BidsData(
                    id = null,
                    title ="Большая проблема 2",
                    description = "Очень большая проблема 2",
                    created_at = null,
                    rating = null,
                    status = null
                ),
                BidsData(
                    id = null,
                    title ="Большая проблема 3",
                    description = "Очень большая проблема 3",
                    created_at = null,
                    rating = null,
                    status = null
                ),
                BidsData(
                    id = null,
                    title ="Большая проблема 4",
                    description = "Очень большая проблема 4",
                    created_at = null,
                    rating = null,
                    status = null
                )
            ))
            recyclerView.adapter = bidsAdapter
        }
    }
}
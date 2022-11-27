package ru.bullyboo.application.ui.bids.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.bullyboo.application.ui.bids.list.BidsListFragment


class BidsViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {

        private const val NUM_TABS = 2
    }

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BidsListFragment.newInstance()
            1 -> return BidsListFragment.newInstance()
        }
        return BidsListFragment.newInstance()
    }
}
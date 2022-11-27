package ru.bullyboo.application.dagger.components.main

import dagger.Subcomponent
import ru.bullyboo.application.dagger.modules.MainModule
import ru.bullyboo.application.dagger.scopes.MainScope
import ru.bullyboo.application.ui.bids.BidsFragment
import ru.bullyboo.application.ui.bids.create.BidsCreateActivity
import ru.bullyboo.application.ui.bids.create.BidsNewCreateFragment
import ru.bullyboo.application.ui.bids.done.DoneFragment
import ru.bullyboo.application.ui.bids.list.BidsListFragment
import ru.bullyboo.application.ui.main.MainFragment
import ru.bullyboo.application.ui.map.MapFragment

@MainScope
@Subcomponent(
    modules = [
        MainModule::class
    ]
)
interface MainComponent {

    fun inject(entity: BidsListFragment)
    fun inject(entity: BidsFragment)
    fun inject(bidsCreateFragment: BidsCreateActivity)
    fun inject(bidsNewCreateFragment: BidsNewCreateFragment)
    fun inject(doneFragment: DoneFragment)
    fun inject(mapFragment: MapFragment)
    fun inject(mainFragment: MainFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): MainComponent
    }
}
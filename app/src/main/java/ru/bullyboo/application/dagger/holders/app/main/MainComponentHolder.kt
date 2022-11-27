package ru.bullyboo.application.dagger.holders.app.main

import ru.bullyboo.application.dagger.components.AppComponent
import ru.bullyboo.application.dagger.components.main.MainComponent
import ru.bullyboo.application.dagger.holders.app.AppComponentHolder
import ru.bullyboo.astrology.dagger.holders.ChildComponentHolder

class MainComponentHolder(
    holder: AppComponentHolder
) : ChildComponentHolder<AppComponent, MainComponent>(
    holder
) {

    override fun build(parent: AppComponent): MainComponent {
        return parent.mainBuilder()
            .build()
    }

    override fun destroy() {
        super.destroy()

    }
}
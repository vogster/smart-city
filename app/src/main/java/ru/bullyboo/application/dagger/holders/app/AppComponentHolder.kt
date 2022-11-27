package ru.bullyboo.application.dagger.holders.app

import ru.bullyboo.application.App
import ru.bullyboo.application.dagger.components.AppComponent
import ru.bullyboo.application.dagger.components.DaggerAppComponent
import ru.bullyboo.application.dagger.holders.app.main.MainComponentHolder
import ru.bullyboo.application.dagger.modules.AppModule
import ru.bullyboo.astrology.dagger.holders.ParentComponentHolder

class AppComponentHolder(
    private val app: App
): ParentComponentHolder<AppComponent>() {

    internal val main by lazy { MainComponentHolder(this) }

    override fun build(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
    }

    override fun destroy() {
        super.destroy()

        main.destroy()
    }
}
package ru.bullyboo.application.dagger.components

import dagger.Component
import ru.bullyboo.application.App
import ru.bullyboo.application.dagger.components.main.MainComponent
import ru.bullyboo.application.dagger.modules.AppModule
import ru.bullyboo.application.dagger.modules.NetworkModule
import ru.bullyboo.application.dagger.modules.StorageModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        StorageModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    fun mainBuilder(): MainComponent.Builder

}
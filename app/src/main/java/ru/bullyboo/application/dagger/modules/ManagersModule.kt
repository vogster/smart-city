package ru.bullyboo.application.dagger.modules

import dagger.Binds
import dagger.Module
import ru.bullyboo.data.managers.ValidationManagerImpl
import ru.bullyboo.domain.managers.ValidationManager
import javax.inject.Singleton

@Module
interface ManagersModule {

    @Binds
    @Singleton
    fun provideValidationManager(
        impl: ValidationManagerImpl
    ): ValidationManager
}
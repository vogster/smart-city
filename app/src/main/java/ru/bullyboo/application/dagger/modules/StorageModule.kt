package ru.bullyboo.application.dagger.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.bullyboo.application.BuildConfig
import ru.bullyboo.data.storages.DeviceDebugStorageImpl
import ru.bullyboo.data.storages.DeviceStorageImpl
import ru.bullyboo.data.storages.PreferenceStorageImpl
import ru.bullyboo.domain.storages.DeviceStorage
import ru.bullyboo.domain.storages.PreferenceStorage
import javax.inject.Singleton

@Module(
    includes = [
        StorageModule.BindsModule::class
    ]
)
@Suppress("unused")
class StorageModule{

    @Provides
    @Singleton
    fun provideDeviceStorage(
        context: Context,
        prefs: PreferenceStorage
    ): DeviceStorage =
        if(BuildConfig.DEBUG){
            DeviceDebugStorageImpl(prefs)
        } else {
            DeviceStorageImpl(context)
        }

    @Module
    interface BindsModule{

        @Binds
        @Singleton
        fun providePreferenceStorage(impl: PreferenceStorageImpl): PreferenceStorage
    }
}
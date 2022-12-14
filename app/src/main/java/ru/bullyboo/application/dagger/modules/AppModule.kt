package ru.bullyboo.application.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideContext(): Context = context
}
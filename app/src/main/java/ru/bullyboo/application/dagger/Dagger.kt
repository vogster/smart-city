package ru.bullyboo.application.dagger

import ru.bullyboo.application.App
import ru.bullyboo.application.dagger.holders.app.AppComponentHolder

object Dagger {

    private lateinit var appInstance: App

    internal val app by lazy { AppComponentHolder(appInstance) }

    fun init(app: App){
        appInstance = app
    }
}
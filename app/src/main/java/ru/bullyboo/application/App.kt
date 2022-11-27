package ru.bullyboo.application

import android.app.Application
import com.google.firebase.FirebaseApp
import ru.bullyboo.application.dagger.Dagger
import javax.inject.Inject

class App: Application() {


    override fun onCreate() {
        super.onCreate()

        initDagger()

//        registerActivityLifecycleCallbacks(AppLifecycleManager())
    }

    private fun initDagger() {
        Dagger.init(this)
        Dagger.app.provide().inject(this)
    }

}
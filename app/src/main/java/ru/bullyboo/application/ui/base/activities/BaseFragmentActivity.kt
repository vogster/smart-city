package ru.bullyboo.application.ui.base.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.bullyboo.application.R
import ru.bullyboo.application.extensions.rootFragment

abstract class BaseFragmentActivity : BaseActivity() {

    abstract fun provideFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        if (savedInstanceState == null) {
            rootFragment(provideFragment())
        }
    }
}
package ru.bullyboo.application.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.bullyboo.application.R
import ru.bullyboo.application.databinding.ActivityMainBinding
import ru.bullyboo.application.ui.bids.BidsFragment
import ru.bullyboo.application.ui.bids.list.BidsListFragment
import ru.bullyboo.application.ui.main.MainFragment
import ru.bullyboo.application.ui.map.MapFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()

        with(binding) {

            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_main -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MainFragment.newInstance())
                            .commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_map -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MapFragment.newInstance())
                            .commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_bids -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, BidsFragment.newInstance())
                            .commit()
                        return@setOnItemSelectedListener true
                    }
                    else -> {
                        Log.d("", "")
                        return@setOnItemSelectedListener true
                    }
                }
            }
        }
    }
}
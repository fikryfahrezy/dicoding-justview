package com.dicoding.justview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * NOTE: How to Make Splash Screen
         * https://developer.android.com/topic/performance/vitals/launch-time#themed
         * https://android.jlelse.eu/launch-screen-in-android-the-right-way-aca7e8c31f52
         * https://stackoverflow.com/a/15832037/12976234
         * https://www.codepolitan.com/mudah-membuat-splash-screen-dengan-android-studio-5a8d2310894d1
         */
        setTheme(R.style.Theme_JustNote)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
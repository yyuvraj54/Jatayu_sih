package com.example.jatayu_sih

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val bottomNav= findViewById<BottomNavigationView>(R.id.bottom_navbar)


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.operation_fragment,
                R.id.notification_fragment,
                R.id.logs_fragment,
                R.id.profile_fragment
            )
        )

        bottomNav.setupWithNavController(navController)



    }
}
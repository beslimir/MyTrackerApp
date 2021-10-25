package com.example.mytrackerapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.mytrackerapp.R
import com.example.mytrackerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setSupportActionBar(mainBinding.toolbar)

        //setup the bottom navigation menu with navHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(mainBinding.bottomNavigationView, navController)

        //handle the bottom navigation view: If not needed, hide it
        navHostFragment.findNavController().addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id) {
                R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                    mainBinding.bottomNavigationView.visibility = View.VISIBLE
                else -> mainBinding.bottomNavigationView.visibility = View.GONE
            }
        }

    }
}
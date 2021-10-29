package com.example.mytrackerapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.mytrackerapp.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.mytrackerapp.R
import com.example.mytrackerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        navigateToTrackingFragmentIfNeeded(intent)

        setSupportActionBar(mainBinding.toolbar)

        //setup the bottom navigation menu with navHostFragment
        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(mainBinding.bottomNavigationView, navController)
        
        //nothing will happen if we set no operation on reselected fragment:
        mainBinding.bottomNavigationView.setOnItemReselectedListener{/* no operation*/}

        //handle the bottom navigation view: If not needed, hide it
        navHostFragment.findNavController().addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id) {
                R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                    mainBinding.bottomNavigationView.visibility = View.VISIBLE
                else -> mainBinding.bottomNavigationView.visibility = View.GONE
            }
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
        }
    }
}
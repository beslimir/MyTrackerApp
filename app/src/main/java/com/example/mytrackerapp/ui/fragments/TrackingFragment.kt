package com.example.mytrackerapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mytrackerapp.R
import com.example.mytrackerapp.databinding.FragmentTrackingBinding
import com.example.mytrackerapp.ui.view_models.MainViewModel
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint

/**
 * We need to take care of MapView lifecycle,
 * therefore we will override the lifecycle methods
 * of the activity in order to handle also
 * the lifecycle of the Map
 *
 * **/

@AndroidEntryPoint
class TrackingFragment: Fragment(R.layout.fragment_tracking) {

    private lateinit var trackingBinding: FragmentTrackingBinding
    private val viewModel: MainViewModel by viewModels()
    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackingBinding = FragmentTrackingBinding.bind(view)
        trackingBinding.mapView.onCreate(savedInstanceState)

        trackingBinding.mapView.getMapAsync{ googleMap ->
            map = googleMap
        }
    }

    override fun onResume() {
        super.onResume()
        trackingBinding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        trackingBinding.mapView.onStart()
    }

    override fun onPause() {
        super.onPause()
        trackingBinding.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        trackingBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        trackingBinding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        trackingBinding.mapView.onSaveInstanceState(outState)
    }

}
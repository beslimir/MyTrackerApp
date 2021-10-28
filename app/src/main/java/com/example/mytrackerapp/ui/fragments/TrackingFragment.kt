package com.example.mytrackerapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mytrackerapp.Constants.ACTION_PAUSE_SERVICE
import com.example.mytrackerapp.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.mytrackerapp.Constants.MAP_ZOOM
import com.example.mytrackerapp.Constants.POLYLINE_COLOR
import com.example.mytrackerapp.Constants.POLYLINE_WIDTH
import com.example.mytrackerapp.R
import com.example.mytrackerapp.databinding.FragmentTrackingBinding
import com.example.mytrackerapp.services.Polyline
import com.example.mytrackerapp.services.TrackingService
import com.example.mytrackerapp.ui.view_models.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint

/**
 * We need to take care of MapView lifecycle,
 * therefore we will override the lifecycle methods
 * of the activity in order to handle also
 * the lifecycle of the Map
 *
 * **/

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private lateinit var trackingBinding: FragmentTrackingBinding
    private val viewModel: MainViewModel by viewModels()
    private var map: GoogleMap? = null
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackingBinding = FragmentTrackingBinding.bind(view)
        trackingBinding.mapView.onCreate(savedInstanceState)

        trackingBinding.mapView.getMapAsync { googleMap ->
            map = googleMap
            addAllPolylines()
        }

        trackingBinding.bToggleRun.setOnClickListener {
            toggleRun()
        }

        subscribeToObservers()
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackingService::class.java).also { thisIntent ->
            thisIntent.action = action
            requireContext().startService(thisIntent)
        }

    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) { //ensure there are min. 2 points
            val preLastPosition = pathPoints.last()[pathPoints.last().size - 2] //take two last points
            val lastPosition = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastPosition)
                .add(lastPosition)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun addAllPolylines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            trackingBinding.bToggleRun.text = "Start"
            trackingBinding.bFinishRun.visibility = View.VISIBLE
        } else {
            trackingBinding.bToggleRun.text = "Stop"
            trackingBinding.bFinishRun.visibility = View.GONE
        }
    }

    private fun toggleRun() {
        if (isTracking) sendCommandToService(ACTION_PAUSE_SERVICE)
        else sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })
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
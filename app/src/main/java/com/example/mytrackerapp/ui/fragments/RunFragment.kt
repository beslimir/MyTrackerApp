package com.example.mytrackerapp.ui.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytrackerapp.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.example.mytrackerapp.R
import com.example.mytrackerapp.SortType
import com.example.mytrackerapp.Utility
import com.example.mytrackerapp.adapters.RunAdapter
import com.example.mytrackerapp.databinding.FragmentRunBinding
import com.example.mytrackerapp.ui.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var runBinding: FragmentRunBinding
    private lateinit var runAdapter: RunAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBinding = FragmentRunBinding.bind(view)

        requestPermissions()
        setupRecyclerView()

        runBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }

        runBinding.spFilter.setSelection(viewModel.sortType.ordinal)
//        when(viewModel.sortType) {
//            SortType.DATE -> runBinding.spFilter.setSelection(0)
//            SortType.DISTANCE -> runBinding.spFilter.setSelection(1)
//            SortType.CALORIES_BURNED -> runBinding.spFilter.setSelection(2)
//            SortType.RUNNING_TIME -> runBinding.spFilter.setSelection(3)
//            SortType.AVG_SPEED -> runBinding.spFilter.setSelection(4)
//        }

        runBinding.spFilter.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                viewModel.sortRuns(SortType.values()[pos])
//                when(pos) {
//                    0 -> viewModel.sortRuns(SortType.DATE)
//                    1 -> viewModel.sortRuns(SortType.DISTANCE)
//                    2 -> viewModel.sortRuns(SortType.CALORIES_BURNED)
//                    3 -> viewModel.sortRuns(SortType.RUNNING_TIME)
//                    4 -> viewModel.sortRuns(SortType.AVG_SPEED)
//                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        viewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() = runBinding.rvRuns.apply {
        layoutManager = LinearLayoutManager(requireContext())
        runAdapter = RunAdapter()
        adapter = runAdapter
    }

    private fun requestPermissions() {
        if (Utility.hasLocationPermissions(requireContext())) {
            return
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                EasyPermissions.requestPermissions(
                    this,
                    "You need to accept the permissions in order to use this app.",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "You need to accept the permissions in order to use this app.",
                    REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}












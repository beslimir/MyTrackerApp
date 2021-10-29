package com.example.mytrackerapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mytrackerapp.CustomMarkerView
import com.example.mytrackerapp.R
import com.example.mytrackerapp.Utility
import com.example.mytrackerapp.databinding.FragmentStatisticsBinding
import com.example.mytrackerapp.ui.view_models.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()
    private lateinit var statisticsBinding: FragmentStatisticsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        statisticsBinding = FragmentStatisticsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
        setupBarChart()
    }

    private fun subscribeToObservers() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = Utility.getFormattedStopWatchTime(it)
                statisticsBinding.tvTotalTime.text = totalTimeRun
            }
        })
        viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it / 1000f
                val totalDistance = round(km * 10f) / 10f
                val totalDistanceString = "${totalDistance}km"
                statisticsBinding.tvTotalDistance.text = totalDistanceString
            }
        })
        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val avgSpeed = round(it * 10f) / 10f
                val avgSpeedString = "${avgSpeed}km/h"
                statisticsBinding.tvAverageSpeed.text = avgSpeedString
            }
        })
        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCalories = "${it}kcal"
                statisticsBinding.tvTotalCalories.text = totalCalories
            }
        })
        viewModel.runsSortedByDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val allAvgSpeeds = it.indices.map { i ->
                    BarEntry(i.toFloat(), it[i].avgSpeed)
                }
                val barDataSet = BarDataSet(allAvgSpeeds, "Avg Speed Over Time").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }
                statisticsBinding.barChart.data = BarData(barDataSet)
                statisticsBinding.barChart.marker = CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                statisticsBinding.barChart.invalidate()
            }
        })
    }

    private fun setupBarChart() {
        statisticsBinding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        statisticsBinding.barChart.axisRight.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        statisticsBinding.barChart.axisLeft.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        statisticsBinding.barChart.apply {
            description.text = "Avg Speed Over Time"
            legend.isEnabled = false
        }
    }

}
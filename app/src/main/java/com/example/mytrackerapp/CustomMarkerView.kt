package com.example.mytrackerapp

import android.content.Context
import android.view.LayoutInflater
import com.example.mytrackerapp.databinding.MarkerViewBinding
import com.example.mytrackerapp.db.Run
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    val runs: List<Run>,
    context: Context,
    layoutId: Int
) : MarkerView(context, layoutId) {

    private lateinit var binding: MarkerViewBinding

    init {
        binding = MarkerViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat()) //data from documentation
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)

        if (e == null) return

        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]

        //date
        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timeStamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        binding.tvDate.text = dateFormat.format(calendar.time)

        val avgSpeed = "${run.avgSpeed}km/h"
        binding.tvAvgSpeed.text = avgSpeed

        val distanceInKm = "${run.distance / 1000f}km"
        binding.tvDistance.text = distanceInKm

        binding.tvDuration.text = Utility.getFormattedStopWatchTime(run.timeMillis)

        val caloriesBurned = "${run.caloriesBurned}kcal"
        binding.tvCaloriesBurned.text = caloriesBurned

    }

}
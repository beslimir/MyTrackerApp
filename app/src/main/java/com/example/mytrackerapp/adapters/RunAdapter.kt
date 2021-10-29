package com.example.mytrackerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytrackerapp.Utility
import com.example.mytrackerapp.databinding.ItemRunBinding
import com.example.mytrackerapp.db.Run
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val itemBinding = ItemRunBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RunViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.bind(run)
    }

    override fun getItemCount() = differ.currentList.size


    val differCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)


    inner class RunViewHolder(private val itemRunBinding: ItemRunBinding) :
        RecyclerView.ViewHolder(itemRunBinding.root) {

        fun bind(run: Run) {
            Glide.with(itemView)
                .load(run.image)
                .into(itemRunBinding.ivRunImage)

            //date
            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timeStamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            itemRunBinding.tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeed}km/h"
            itemRunBinding.tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${run.distance / 1000f}km"
            itemRunBinding.tvDistance.text = distanceInKm

            itemRunBinding.tvTime.text = Utility.getFormattedStopWatchTime(run.timeMillis)

            val caloriesBurned = "${run.caloriesBurned}kcal"
            itemRunBinding.tvCalories.text = caloriesBurned
        }
    }

}
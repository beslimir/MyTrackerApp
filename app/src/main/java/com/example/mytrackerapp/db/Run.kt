package com.example.mytrackerapp.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class
 *
 * timeStamp - date when we created the instance (easier to maintain and sort than in date format)
 * timeMillis - the length of the activity
 * ...
 *
 * **/

@Entity(tableName = "running_table")
data class Run(
    var image: Bitmap? = null,
    var timeStamp: Long = 0L,
    var avgSpeed: Float = 0f,
    var distance: Int = 0,
    var timeMillis: Long = 0L,
    var caloriesBurned: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
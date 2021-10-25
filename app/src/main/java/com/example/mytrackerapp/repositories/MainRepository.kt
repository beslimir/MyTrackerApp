package com.example.mytrackerapp.repositories

import com.example.mytrackerapp.db.Run
import com.example.mytrackerapp.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDAO: RunDAO
) {
    //run fragment
    suspend fun insertRun(run: Run) = runDAO.insertRun(run)
    suspend fun deleteRun(run: Run) = runDAO.deleteRun(run)
    fun getAllRunsSortedByDate() = runDAO.getAllRunsSortedByDate()
    fun getAllRunsSortedByDistance() = runDAO.getAllRunsSortedByDistance()
    fun getAllRunsSortedByTimeInMillis() = runDAO.getAllRunsSortedByTimeInMillis()
    fun getAllRunsSortedByAvgSpeed() = runDAO.getAllRunsSortedByAvgSpeed()
    fun getAllRunsSortedByCaloriesBurned() = runDAO.getAllRunsSortedByCaloriesBurned()
    //statistics fragment:
    fun getTotalTimeInMillis() = runDAO.getTotalTimeInMillis()
    fun getTotalDistance() = runDAO.getTotalDistance()
    fun getTotalAvgSpeed() = runDAO.getTotalAverageSpeed()
    fun getTotalCaloriesBurned() = runDAO.getTotalCaloriesBurned()


}
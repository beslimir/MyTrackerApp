package com.example.mytrackerapp.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mytrackerapp.R
import com.example.mytrackerapp.ui.view_models.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel: StatisticsViewModel by viewModels()

}
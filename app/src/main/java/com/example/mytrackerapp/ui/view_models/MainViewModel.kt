package com.example.mytrackerapp.ui.view_models

import androidx.lifecycle.ViewModel
import com.example.mytrackerapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository: MainRepository
): ViewModel() {

}
package com.example.facialdetection.retro.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeatherViewModelFactory(private var id:String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            return WeatherViewModel(id) as T
        }

        throw IllegalArgumentException("Unknown viewModel class!")
    }
}
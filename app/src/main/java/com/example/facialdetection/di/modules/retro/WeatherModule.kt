package com.example.facialdetection.di.modules

import com.example.facialdetection.retro.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherModule = module {
    viewModel { WeatherViewModel() }
}
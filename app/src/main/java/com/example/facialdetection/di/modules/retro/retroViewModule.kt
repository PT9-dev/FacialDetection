package com.example.facialdetection.di.modules

import com.example.facialdetection.retro.RetroHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retroViewModule = module {
    viewModel {
        RetroHomeViewModel()
    }
}
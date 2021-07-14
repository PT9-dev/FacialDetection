package com.example.facialdetection.di.modules

import com.example.facialdetection.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModule = module {
    viewModel { MainActivityViewModel() }
}
package com.example.facialdetection.di.modules.camera

import com.example.facialdetection.camera.CameraViewModel
import com.example.facialdetection.camera.FaceCompareViewModel
import com.example.facialdetection.camera.ListFaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val cameraViewModule = module {
    viewModel { CameraViewModel() }
    viewModel { ListFaceViewModel() }
    viewModel { FaceCompareViewModel(get()) }
}
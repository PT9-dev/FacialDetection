package com.example.facialdetection

import android.app.Application
import com.example.facialdetection.di.modules.camera.cameraViewModule
import com.example.facialdetection.di.modules.camera.databaseModule
import com.example.facialdetection.di.modules.camera.faceDetectModule
import com.example.facialdetection.di.modules.mainViewModule
import com.example.facialdetection.di.modules.retro.retroModule
import com.example.facialdetection.di.modules.retroViewModule
import com.example.facialdetection.di.modules.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    mainViewModule, retroViewModule, weatherModule, retroModule,
                    cameraViewModule, faceDetectModule, databaseModule
                )
            )
        }
    }
}
package com.example.facialdetection.di.modules.camera

import com.example.facialdetection.Utils
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import org.koin.dsl.module

val faceDetectModule = module {
    single { detectorClient() }
    single { faceDetector(get()) }
    factory { getUtils() }
}

fun getUtils() = Utils()

fun detectorClient(): FaceDetectorOptions = FaceDetectorOptions.Builder()
    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
    .setContourMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
    .build()

fun faceDetector(client:FaceDetectorOptions): FaceDetector = FaceDetection.getClient(client)
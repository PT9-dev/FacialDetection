package com.example.facialdetection.di.modules.camera

import android.app.Application
import androidx.room.Room
import com.example.facialdetection.database.FaceDao
import com.example.facialdetection.database.FaceDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var databaseModule = module {
    single { dbInstance(androidApplication()) }
    single { getFaceDao(get()) }
}

fun getFaceDao(database: FaceDatabase): FaceDao {
    return database.faceTableDao
}

fun dbInstance(application: Application): FaceDatabase {
    return Room.databaseBuilder(
        application,
        FaceDatabase::class.java,
        "face_database"
    ).fallbackToDestructiveMigration().build()
}

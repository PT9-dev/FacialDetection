package com.example.facialdetection.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FaceTable::class], version = 2)
abstract class FaceDatabase: RoomDatabase() {
    abstract val faceTableDao: FaceDao
}
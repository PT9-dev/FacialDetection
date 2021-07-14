package com.example.facialdetection.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "face_table")
data class FaceTable(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "face_name")
    @NonNull
    val faceName: String,
    @ColumnInfo(name = "encoding")
    @NonNull
    val encoding: String,
    @ColumnInfo(name = "face_uri")
    @NonNull
    val faceUri: String = ""
)
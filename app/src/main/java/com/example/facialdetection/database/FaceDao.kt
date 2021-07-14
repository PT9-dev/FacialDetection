package com.example.facialdetection.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FaceDao {
    @Insert
    fun insert(data: FaceTable)

    @Query("SELECT * FROM face_table")
    fun getAllLive(): LiveData<List<FaceTable>>

    @Query("SELECT * FROM face_table")
    suspend fun getAll(): List<FaceTable>

    @Query("DELETE FROM face_table")
    fun delete()
}
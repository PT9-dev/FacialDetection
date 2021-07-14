package com.example.facialdetection.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facialdetection.database.FaceDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class ListFaceViewModel : ViewModel(), KoinComponent {

    private val database: FaceDao by inject()

    val allFaces = database.getAllLive()

    fun clearAll() {
        viewModelScope.launch {
            delete()
        }
    }

    private suspend fun delete() {
        withContext(Dispatchers.IO) {
            database.delete()
        }
    }

}
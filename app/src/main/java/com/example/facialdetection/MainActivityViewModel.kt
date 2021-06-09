package com.example.facialdetection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private var _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    fun setTitle(text: String) {
        _title.postValue(text)
    }
}

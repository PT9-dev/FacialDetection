package com.example.facialdetection.retro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facialdetection.retro.pojo.WeatherId
import com.example.facialdetection.retro.weather.api.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

@KoinApiExtension
class RetroHomeViewModel : ViewModel(), KoinComponent {
    private val api: ApiHelper by inject()
    private val _warning = MutableLiveData<Boolean>()
    val warning: LiveData<Boolean>
        get() = _warning
    private val _list = MutableLiveData<MutableList<String>>()
    val list: LiveData<MutableList<String>>
        get() = _list
    private val _id = MutableLiveData<String>()
    val id:LiveData<String>
    get() = _id

    private var tempList = mutableListOf<String>()


    fun init() {
        _warning.postValue(true)
        _list.postValue(tempList)
        _id.postValue("")
    }


    fun callProcess(queryLocation: String) {

        viewModelScope.launch {
            if (queryLocation != "") {
                val result = process(queryLocation)
                tempList.clear()
                if (result.size != 0) {
                    _warning.postValue(false)
                    for (item in result) {
                        tempList.add(item.title)
                    }
                    _list.postValue(tempList)
                } else {
                    _warning.postValue(true)
                }


                if(result.size >= 1)
                    if (result[0].title == queryLocation.capitalize(Locale.ROOT))
                        _id.postValue(result[0].woeid.toString())

            } else
                _id.postValue("")
        }


    }


    private suspend fun process(queryLocation: String): WeatherId {
        return  withContext(Dispatchers.IO){
                 api.idOf(queryLocation)
            }
        }
}












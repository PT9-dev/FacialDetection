package com.example.facialdetection.retro.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facialdetection.retro.weather.api.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@KoinApiExtension
class WeatherViewModel: ViewModel(), KoinComponent {
    private lateinit var weatherId: String
    private val service: ApiHelper by inject()

    private var _state = MutableLiveData<String>()
    val state : LiveData<String>
    get() = _state
    private var _loc = MutableLiveData<String>()
    val loc : LiveData<String>
        get() = _loc
    private var _date = MutableLiveData<String>()
    val date : LiveData<String>
        get() = _date
   private var _picture = MutableLiveData<String>()
    val picture : LiveData<String>
    get() = _picture


    fun init(args: String) {
        this.weatherId = args
        _state.value = ""
        _loc.value = ""
        _date.value =""
        _picture.value = ""
    }

    fun getWeather(){
        viewModelScope.launch {
            getWeatherImpl()
        }
    }

    private suspend fun getWeatherImpl(){
        withContext(Dispatchers.IO){

            val weather = try {
                service.weather(weatherId)
            } catch (e: Exception) {
                println("error")
                throw Exception()
            }
            val today = weather.consolidatedWeather[0]
            _loc.postValue(weather.title)
            _state.postValue(today.weatherStateName)
            _picture.postValue(ICON_BASE_URL+today.weatherStateAbbr+".png")
            _date.postValue(today.applicableDate)
        }


    }


    companion object{
        const val ICON_BASE_URL = "https://www.metaweather.com/static/img/weather/png/"
    }

}
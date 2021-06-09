package com.example.facialdetection.retro.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facialdetection.retro.RetrofitClient
import com.example.facialdetection.retro.pojo.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(private val weatherId: String): ViewModel() {
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

    init {
        _state.value = ""
        _loc.value = ""
        _date.value =""
        _picture.value = ""
    }


    fun getWeather(){
        val service = RetrofitClient().getWeatherAPI()
        val call = service.weather(weatherId)
        call.enqueue(object: Callback<Weather?> {
            override fun onResponse(call: Call<Weather?>, response: Response<Weather?>) {
                val idn = response.body()?.consolidatedWeather!![0]
                _loc.value = response.body()!!.title
                _state.value = idn.weatherStateName
                _picture.value = ICON_BASE_URL+idn.weatherStateAbbr+".png"
                _date.value = idn.applicableDate

            }

            override fun onFailure(call: Call<Weather?>, t: Throwable) {
                println("error")

            }
        })
    }


    companion object{
        const val ICON_BASE_URL = "https://www.metaweather.com/static/img/weather/png/"
    }

//    fun getPicture(state: String) {
//        when(state) {
//            "Light Cloud" -> url
//            "Light Cloud" -> url
//        }
//    }


}
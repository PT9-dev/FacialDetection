package com.example.facialdetection.retro.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.facialdetection.retro.RetrofitClient
import com.example.facialdetection.retro.pojo.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(private val weatherId: String): ViewModel() {
   var state = MutableLiveData<String>()
   var picture = MutableLiveData<String>()

    init {
        state.value = ""
    }


    fun getWeather(){
        val service = RetrofitClient().getWeatherAPI()
        val call = service.weather(weatherId)
        call.enqueue(object: Callback<Weather?> {
            override fun onResponse(call: Call<Weather?>, response: Response<Weather?>) {
                val idn = response.body()?.consolidatedWeather!![0]
                state.value = idn.weatherStateName
                picture.value = ICON_BASE_URL+idn.weatherStateAbbr+".png"


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
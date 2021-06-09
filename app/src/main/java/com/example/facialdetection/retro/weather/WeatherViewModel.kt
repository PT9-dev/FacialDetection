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

    init {
        state.value = ""
    }


    fun getWeather(){
        val service = RetrofitClient().getWeatherAPI()
        val call = service.weather(weatherId)
        call.enqueue(object: Callback<Weather?> {
            override fun onResponse(call: Call<Weather?>, response: Response<Weather?>) {
                val idn = response.body()?.consolidated_weather!![0]
                state.value = idn.weather_state_name

            }

            override fun onFailure(call: Call<Weather?>, t: Throwable) {
                println("error")

            }
        })
    }


}
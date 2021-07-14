package com.example.facialdetection.retro.weather.api

import com.example.facialdetection.retro.pojo.Weather
import com.example.facialdetection.retro.pojo.WeatherId


class ApiHelper(private val apiService: GetWeatherAPI) {
    suspend fun idOf(query:String): WeatherId = apiService.idOf(query)
    suspend fun weather(id:String): Weather = apiService.weather(id)
}
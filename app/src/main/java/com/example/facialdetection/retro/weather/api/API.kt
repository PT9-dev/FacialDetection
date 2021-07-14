package com.example.facialdetection.retro.weather.api

import com.example.facialdetection.retro.pojo.Weather
import com.example.facialdetection.retro.pojo.WeatherId
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GetWeatherAPI{
    // GET request to get all images
    @GET("search/")
    suspend fun idOf(@Query("query") query: String): WeatherId

    @GET("{id}/")
    suspend fun weather(@Path("id") id: String): Weather

}
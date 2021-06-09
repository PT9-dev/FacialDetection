package com.example.facialdetection.retro

import com.example.facialdetection.retro.pojo.Weather
import com.example.facialdetection.retro.pojo.WeatherId
import retrofit2.Call
import retrofit2.http.*


interface GetIdAPI {
    // GET request to get all images
    @GET("/api/location/search/")
    fun IdOf(@Query("query") query: String): Call<WeatherId?>
}


interface GetWeatherAPI{

}
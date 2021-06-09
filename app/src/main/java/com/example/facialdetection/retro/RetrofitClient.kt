package com.example.facialdetection.retro

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient() {
    private val BASE_URL = "https://www.metaweather.com/"
    private var mInstance: RetrofitClient? = null
    var httpClient = OkHttpClient.Builder()

    private var retrofit:Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        //.client(httpClient.build())
        .build()


    @Synchronized
    fun getInstance(): RetrofitClient? {
        if (mInstance == null) {
            mInstance = RetrofitClient()
        }
        return mInstance
    }

    fun getIdAPI(): GetIdAPI {
        return retrofit.create(GetIdAPI::class.java)

    }

    fun getWeatherAPI(): GetWeatherAPI {
        return retrofit.create(GetWeatherAPI::class.java)
    }


}
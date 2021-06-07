package com.example.facialdetection.retro

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class RetrofitClient() {
    private val BASE_URL = "http://remotedevs.org:8080/api/"
    private var mInstance: RetrofitClient? = null
    private lateinit var retrofit:Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Synchronized
    fun getInstance(): RetrofitClient? {
        if (mInstance == null) {
            mInstance = RetrofitClient()
        }
        return mInstance
    }

    fun getAPI(): API {
        return retrofit.create(API::class.java)

    }


}
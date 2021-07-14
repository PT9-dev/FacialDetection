package com.example.facialdetection.di.modules.retro

import com.example.facialdetection.retro.weather.api.ApiHelper
import com.example.facialdetection.retro.weather.api.GetWeatherAPI
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val retroModule = module {
    single { provideRetrofitClient() }
    single { getWeatherAPI(get()) }
    single { ApiHelper(get()) }
}

fun provideRetrofitClient(): Retrofit{
    val baseUrl = "https://www.metaweather.com/api/location/"
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        //.client(httpClient.build())
        .build()
}

fun getWeatherAPI(retrofit: Retrofit): GetWeatherAPI {
    return retrofit.create(GetWeatherAPI::class.java)
}





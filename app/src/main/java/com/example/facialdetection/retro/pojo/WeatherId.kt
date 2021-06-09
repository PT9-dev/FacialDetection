package com.example.facialdetection.retro.pojo

import com.google.gson.annotations.SerializedName

// A POJO class



class WeatherId : ArrayList<WeatherIdItem>()

data class WeatherIdItem(
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Int
)
package com.example.facialdetection.retro.pojo

import com.google.gson.annotations.SerializedName

// A POJO class



class WeatherId : ArrayList<WeatherIdItem>()

data class WeatherIdItem(
    val latt_long: String,
    val location_type: String,
    val title: String,
    val woeid: Int
)
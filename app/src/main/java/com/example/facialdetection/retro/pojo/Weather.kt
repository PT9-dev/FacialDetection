package com.example.facialdetection.retro.pojo

import com.google.gson.annotations.SerializedName

// A POJO data class

data class Weather(
    @SerializedName("consolidated_weather")
    val consolidatedWeather: List<ConsolidatedWeather>,
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Int
)

data class ConsolidatedWeather(
    @SerializedName("air_pressure")
    val airPressure: Double,
    @SerializedName("applicable_date")
    val applicableDate: String,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("max_temp")
    val maxTemp: Double,
    @SerializedName("min_temp")
    val minTemp: Double,
    @SerializedName("predictability")
    val predictability: Int,
    @SerializedName("the_temp")
    val theTemp: Double,
    @SerializedName("visibility")
    val visibility: Double,
    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String,
    @SerializedName("weather_state_name")
    val weatherStateName: String,
    @SerializedName("wind_direction")
    val windDirection: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)






package com.regi.abyte.model

data class APIWeatherResponse(
    val main: Main
)

data class Main(
    val temp: Double
)
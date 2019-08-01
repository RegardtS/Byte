package com.regi.abyte.network

import com.regi.abyte.model.APIWeatherResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface WeatherApi {

    @GET("weather?appid=e62e114b1d73a3839f3f3f7a34249f85&units=metric&id=3369157")
    fun getWeather(): Observable<APIWeatherResponse>

    @GET("weather?appid=e62e114b1d73a3839f3f3f7a34249f85&units=metric&id=3369157")
    fun getWeatherBackground(): Call<APIWeatherResponse>

}
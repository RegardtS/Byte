package com.regi.abyte.background

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.regi.abyte.BaseViewModel
import com.regi.abyte.database.LocationDatabase
import com.regi.abyte.model.APIWeatherResponse
import com.regi.abyte.network.WeatherApi
import com.regi.abyte.repo.LocationRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BackgroundWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    @Inject
    lateinit var weatherApi: WeatherApi

    private var repository: LocationRepo

    init {
        val locationDao = LocationDatabase.getDatabase(ctx).locationDao()
        repository = LocationRepo(locationDao)
    }

    override fun doWork(): Result {

        //TODO

        weatherApi.getWeatherBackground().enqueue(object : Callback<APIWeatherResponse>{
            override fun onFailure(call: Call<APIWeatherResponse>, t: Throwable) {
                //TODO
            }

            override fun onResponse(call: Call<APIWeatherResponse>, response: Response<APIWeatherResponse>) {
                //TODO save to db/prefs
            }

        })
        return Result.success()

    }
}

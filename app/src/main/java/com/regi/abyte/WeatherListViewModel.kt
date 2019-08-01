package com.regi.abyte

import androidx.lifecycle.MutableLiveData
import com.regi.abyte.model.APIWeatherResponse
import com.regi.abyte.network.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherListViewModel : BaseViewModel() {
    @Inject
    lateinit var weatherApi: WeatherApi

    private var subscription: Disposable

    val weather = MutableLiveData<String>()

    init {
        subscription = weatherApi.getWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { result -> onRetrievePostListError(result) }
            )
    }

    private fun onRetrievePostListSuccess(resonse: APIWeatherResponse) {
        weather.value = resonse.main.temp.toString()
    }

    private fun onRetrievePostListError(throwable: Throwable) {
        //TODO handle this
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}
package com.regi.abyte

import androidx.lifecycle.ViewModel
import com.regi.abyte.injection.component.DaggerViewModelInjector
import com.regi.abyte.injection.component.ViewModelInjector
import com.regi.abyte.injection.module.NetworkModule

abstract class BaseViewModel : ViewModel(){

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject(){
        when(this){
            is WeatherListViewModel -> injector.inject(this)
        }
    }
}
package com.regi.abyte.injection.component

import com.regi.abyte.WeatherListViewModel
import com.regi.abyte.background.BackgroundWorker
import com.regi.abyte.injection.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(weatherListViewModel: WeatherListViewModel)

    fun inject(backgroundWorker: BackgroundWorker)

    @Component.Builder
    interface Builder{
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}
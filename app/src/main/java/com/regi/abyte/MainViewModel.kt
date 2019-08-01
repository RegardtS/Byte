package com.regi.abyte

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.regi.abyte.database.LocationDatabase
import com.regi.abyte.model.Location
import com.regi.abyte.repo.LocationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application){

    private var repository: LocationRepo
    val allLocations: LiveData<List<Location>>

    init {
        val locationDao = LocationDatabase.getDatabase(application).locationDao()
        repository = LocationRepo(locationDao)
        allLocations = repository.allLocations
    }

    fun insertBulk(locations: List<Location>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBulk(locations)
    }
}
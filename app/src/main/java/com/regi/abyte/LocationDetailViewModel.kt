package com.regi.abyte

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.regi.abyte.database.LocationDatabase
import com.regi.abyte.model.Location
import com.regi.abyte.repo.LocationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: LocationRepo
    var location = MutableLiveData<Location?>()

    init {
        val locationDao = LocationDatabase.getDatabase(application).locationDao()
        repository = LocationRepo(locationDao)
    }

    fun existingLocation(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        location.postValue(repository.getLocation(id))
    }

    fun updateLocationDetails(nameLocation: String, lat: Double, long: Double) {
        var currentLocation = location.value
        if(currentLocation == null){
            currentLocation = Location(nameLocation,lat,long)
            insertLocation(currentLocation)
        }else{
            currentLocation.name = nameLocation
            currentLocation.longitude = long
            currentLocation.latitude = lat
            updateLocation(currentLocation)
        }
    }

    private fun updateLocation(location: Location) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(location)
    }

    private fun insertLocation(location: Location) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(location)
    }

    fun deleteLocation() = viewModelScope.launch(Dispatchers.IO) {
        location.value?.let { repository.deleteLocation(it) }
    }
}
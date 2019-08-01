package com.regi.abyte.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.regi.abyte.dao.LocationDao
import com.regi.abyte.model.Location

class LocationRepo (private val locationDao: LocationDao){

    val allLocations: LiveData<List<Location>> = locationDao.getAllLocations()

    @WorkerThread
    suspend fun insert(location: Location){
        locationDao.insert(location)
    }

    @WorkerThread
    suspend fun insertBulk(locations: List<Location>){
        locationDao.insertBulk(locations)
    }

    @WorkerThread
    suspend fun update(location: Location){
        locationDao.update(location)
    }

    fun getLocation(id: Int): Location{
         return locationDao.getLocation(id)
    }

    @WorkerThread
    suspend fun deleteLocation(location: Location){
        locationDao.deleteLocation(location)
    }
}
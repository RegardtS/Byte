package com.regi.abyte.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.regi.abyte.model.Location

@Dao
interface LocationDao {

    @Insert
    suspend fun insertBulk(locations: List<Location>)

    @Insert
    suspend fun insert(location: Location)

    @Update
    suspend fun update(location: Location)

    @Query("SELECT * FROM location")
    fun getAllLocations(): LiveData<List<Location>>

    @Query("SELECT * FROM location WHERE locationId = :locationId LIMIT 1")
    fun getLocation(locationId: Int): Location

    @Delete
    suspend fun deleteLocation(location: Location)
}
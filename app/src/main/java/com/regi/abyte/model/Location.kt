package com.regi.abyte.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Location(
    var name: String,
    var latitude: Double,
    var longitude: Double
) {
    @PrimaryKey(autoGenerate = true)
    var locationId: Int = 0
}
package com.example.jatayu_sih.loaction
import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval:Long): Flow<Location>
    class LocationException(meassage: String):Exception()

}
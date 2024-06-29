package com.layon.locationupdatescallbackflowsample.ui

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationManager: LocationManager
) : ViewModel() {

    @SuppressLint("MissingPermission")
    // The callbackFlow block creates a Flow of Location objects.
    fun getLocationFlow(): Flow<Location> = callbackFlow {
        // The LocationListener interface is used to receive location updates.
        val locationListener = LocationListener { location ->
            // Whenever a new location is available, the trySend function is used to emit the Location data to the Flow.
            trySend(location)
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )
        // The awaitClose block ensures that the location listener is unregistered
        // when the flow is cancelled completes to avoid memory leaks.
        awaitClose {
            locationManager.removeUpdates(locationListener)
        }
    }

}
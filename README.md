# LocationUpdatesCallbackFlowSample
## An Android App Sample of how to use CallbackFlow in compose

* Compose
* Kotlin Flow API
* CallbackFlow
* Hilt
* Accompanist Permissions in Compose (Manifest.permission.ACCESS_FINE_LOCATION)
* Version Catalogs dependencies (libs.versions.toml)

### Using CallbackFlow

There is a function in LocationViewModel.kt called getLocationFlow(), that will return 
a Flow<Location> generated by callbackFlow.

```
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
```


<img src="Screenshot_1.png" width="40%"> <img src="Screenshot_2.png" width="40%">

### References:
* https://medium.com/@manuaravindpta/callbackflow-in-kotlin-b830a1498946
* https://amitshekhar.me/blog/callback-to-flow-api-in-kotlin
* https://medium.com/@dheerubhadoria/mastering-location-permissions-and-ui-in-jetpack-compose-847cacd4be2e
* https://developer.android.com/build/migrate-to-catalogs
* https://developer.android.com/training/dependency-injection/hilt-android
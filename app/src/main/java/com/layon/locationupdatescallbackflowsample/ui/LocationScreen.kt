package com.layon.locationupdatescallbackflowsample.ui

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.layon.locationupdatescallbackflowsample.util.isLocationPermissionsGranted

@Composable
fun LocationScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isLocationPermissionsGranted by rememberSaveable {
        mutableStateOf(
            isLocationPermissionsGranted(context)
        )
    }

    //Create a permission launcher
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                isLocationPermissionsGranted = isGranted
            })

    if (isLocationPermissionsGranted) {
        ShowLocationInfos(modifier = modifier)
    } else {
        RequestLocationPermission(
            onClick = { requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
        )
    }
}

@Composable
fun ShowLocationInfos(
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var textLocation = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("My Location is: ", fontWeight = FontWeight.Bold, fontSize = 18.sp,)
        Text(textLocation.value)
    }
    LaunchedEffect(key1 = true) {
        viewModel.getLocationFlow()
            .collect { location ->
                Log.d("layonflog", "location: $location")
                textLocation.value = "${location.latitude}, ${location.longitude}"
            }
    }
}

@Composable
fun RequestLocationPermission(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Please, allow your location access!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Request location permission
            Log.d("layonflog", "Request location permission")
            onClick()
        }
        ) {
            Text(text = "Allow")
        }
    }
}
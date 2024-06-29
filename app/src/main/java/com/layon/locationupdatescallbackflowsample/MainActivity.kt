package com.layon.locationupdatescallbackflowsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.layon.locationupdatescallbackflowsample.ui.LocationScreen
import com.layon.locationupdatescallbackflowsample.ui.theme.LocationUpdatesCallbackFlowSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocationUpdatesCallbackFlowSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Log.d("layonflog", "innerPadding: $innerPadding")
                    LocationScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

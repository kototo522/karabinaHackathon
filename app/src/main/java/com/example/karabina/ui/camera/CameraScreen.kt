package com.example.karabina.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class Location(val latitude: Double, val longitude: Double)


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen() {
    val locationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            bitmapImage = bitmap
        }
    var location by remember { mutableStateOf(Location(33.8815604, 130.8764971)) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val xLiveData = MutableLiveData(0)
    val yLiveData = MutableLiveData(0)
    val zLiveData = MutableLiveData(0)

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?) {
            p0?.let {
                xLiveData.value = it.values[0].toInt()
                yLiveData.value = it.values[1].toInt()
                zLiveData.value = it.values[2].toInt()
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
    }
    DisposableEffect(Unit) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val newLocation = locationResult.lastLocation
                coroutineScope.launch {
                    if (newLocation != null) {
                        location = Location(newLocation.latitude, newLocation.longitude)
                    }
                }
            }
        }
        if (locationPermissionState.hasPermission) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            } else {
                locationPermissionState.launchPermissionRequest()
            }
        }
        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            sensorManager.unregisterListener(sensorEventListener)
        }
    }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = { TopAppBar(title = { Text(text = "カメラ") }) },
            ) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    if (cameraPermissionState.hasPermission) {
                        launcher.launch(null)
                    } else {
                        cameraPermissionState.launchPermissionRequest()
                    }
                    SensorDataDisplay(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        xLiveData = xLiveData,
                        yLiveData = yLiveData,
                        zLiveData = zLiveData
                    )
                }
            }
        }
    }

@Composable
fun SensorDataDisplay(
    latitude: Double,
    longitude: Double,
    xLiveData: LiveData<Int>,
    yLiveData: LiveData<Int>,
    zLiveData: LiveData<Int>
) {
    val xValue = rememberUpdatedState(xLiveData.value ?: 0)
    val yValue = rememberUpdatedState(yLiveData.value ?: 0)
    val zValue = rememberUpdatedState(zLiveData.value ?: 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PostGet(latitude, longitude, xLiveData, yLiveData, zLiveData)
    }
}


fun PostGet(latitude: Double, longitude: Double, xLiveData: LiveData<Int>, yLiveData: LiveData<Int>, zLiveData: LiveData<Int>) {
    val baseUrl = "https://api.himarupi.com"
    val getEndpoint = "/get-plane"
    val postEndpoint = "/get-plane"
    val client = OkHttpClient()

    // POSTリクエストの実行
    val postJsonObject = JSONObject().apply {
        put("latitude", latitude)
        put("longitude", longitude)
        put("rx", xLiveData.value)
        put("ry", yLiveData.value)
        put("rz", zLiveData.value)
    }
    val postRequestBody = RequestBody.create("application/json".toMediaTypeOrNull(), postJsonObject.toString())
    val postRequest = Request.Builder()
        .url("$baseUrl$postEndpoint")
        .post(postRequestBody)
        .build()

    client.newCall(postRequest).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                // POSTのresponseBodyを処理
            } else {
                println("POST Request was not successful. Code: ${response.code}")
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            Log.v("test","POST Request failed: ${e.message}")
        }
    })

    // GETリクエストの実行
    val getParameters = mapOf(
        "latitude" to latitude.toString(),
        "longitude" to longitude.toString(),
        "rx" to xLiveData.value.toString(),
        "ry" to yLiveData.value.toString(),
        "rz" to zLiveData.value.toString()
    )
    val getQuery = getParameters.entries.joinToString("&") { "${it.key}=${it.value}" }
    val getRequest = Request.Builder()
        .url("$baseUrl$getEndpoint?$getQuery")
        .build()

    client.newCall(getRequest).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                // GETのresponseBodyを処理
            } else {
                println("GET Request was not successful. Code: ${response.code}")
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            println("GET Request failed: ${e.message}")
        }
    })
}




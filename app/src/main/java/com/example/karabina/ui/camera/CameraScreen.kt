package com.example.karabina.ui.camera

import android.content.Context
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

// https://developer.android.com/reference/android/hardware/SensorManager

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen() {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmapImage = bitmap
    }
    val context = LocalContext.current // Get the current context
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val xLiveData = MutableLiveData(0)
    val yLiveData = MutableLiveData(0)
    val zLiveData = MutableLiveData(0)

    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val sensorEventListener = object: SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?){
            p0?.let{
                Log.e("testx", it.values[0].toInt().toString())
                Log.e("testy", it.values[1].toInt().toString())
                Log.e("testz", it.values[2].toInt().toString())
                xLiveData.value = it.values[0].toInt()
                yLiveData.value = it.values[1].toInt()
                zLiveData.value = it.values[2].toInt()
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }

    }

    DisposableEffect(sensorManager) {
        sensorManager.registerListener(
            sensorEventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "カメラ") }) },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                SensorDataDisplay(xLiveData, yLiveData, zLiveData)
//                if (cameraPermissionState.hasPermission) {
//                    launcher.launch(null)
//                } else {
//                    cameraPermissionState.launchPermissionRequest()
//                }
            }
        }
    }
}
@Composable
fun SensorDataDisplay(
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
        Text(
            text = " X= $xValue　Y= $yValue　Z= $zValue",
        )
    }
}

package com.example.karabina.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.karabina.ui.home.component.AddDialog
import com.example.karabina.ui.home.component.map.MapView
import com.example.karabina.ui.home.component.map.OnMapReady
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    lateinit var googleMap: GoogleMap
    val showDialog = remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "マップ") }) },
            floatingActionButton = {
                Box(contentAlignment = Alignment.TopCenter){
                    FloatingActionButton(onClick = {showDialog.value = true},
                        modifier = Modifier
                            .padding(bottom = 80.dp)
                            .align(Alignment.TopCenter)) {
                        Icon(
                            imageVector = Icons.Default.AddLocationAlt,
                            contentDescription = "投稿"
                        )
                    }
                }

            },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                if(showDialog.value) AddDialog(setShowDialog = { showDialog.value = it })
                MapView()
                OnMapReady(googleMap, LatLng(130.8752, 33.8834), "https://www.andtrip.jp/images/b92f20d16e2c9ddc6376edb4ce5b5c99ee64bb94.jpg")
            }

        }
    }
}

@Composable
@Preview
fun ViewHome(){
    HomeScreen()
}
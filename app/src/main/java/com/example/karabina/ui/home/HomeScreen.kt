package com.example.karabina.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.karabina.ui.home.component.AddDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val showDialog = remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "SNS") }) },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Row (horizontalArrangement = Arrangement.End){
                    IconButton(onClick = {showDialog.value = true}) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "投稿する"
                        )
                    }
                }
                if(showDialog.value) AddDialog(setShowDialog = { showDialog.value = it })
            }
        }
    }
}
package com.example.karabina.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.karabina.ui.post.postlist.PostList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "投稿一覧") }) },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                PostList()
            }
        }
    }
}

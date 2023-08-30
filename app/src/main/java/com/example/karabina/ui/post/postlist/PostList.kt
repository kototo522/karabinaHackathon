package com.example.karabina.ui.post.postlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.karabina.ui.home.component.data.PostCard


val dummyPosts = listOf(
    PostCard(
        id = "1",
        location = "北九州空港",
        imageUrl = "https://travel.willer.co.jp/willer-colle/wp-content/uploads/2021/05/pixta_76688470_M.jpg",
        text = "ようこそ！北九州市へ！！"
    ),
    PostCard(
        id = "2",
        location = "小倉城",
        imageUrl = "https://www.kokura-castle.jp/wp-content/uploads/2020/01/82993070_596809700864334_7653958495468781568_n.jpg",
        text = "さくら〜"
    ),
    PostCard(
        id = "3",
        location = "小倉競馬場",
        imageUrl = "https://www.jra.go.jp/facilities/race/kokura/course/img/pic_course1.jpg",
        text = "イクイロックスしか勝たん！！"
    )
)
@Composable
fun PostList() {
    LazyColumn() {
        items(dummyPosts) { post ->
            PostCardItem(post)
        }
    }
}

@Composable
fun PostCardItem(post: PostCard) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            post.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            post.location?.let { location ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            post.text?.let { text ->
                Text(text = text, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
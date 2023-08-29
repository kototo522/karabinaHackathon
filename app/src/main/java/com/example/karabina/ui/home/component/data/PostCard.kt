package com.example.karabina.ui.home.component.data

import com.google.android.gms.maps.model.LatLng

data class PostCard(
    val id: String?,
    val place: LatLng,
    val imageUrl: String,
    val text: String?,
)

val dummyPosts = listOf(
    PostCard(
        id = "1",
        place = LatLng(-27.47093, 153.0235),
        imageUrl = "https://a.cdn-hotels.com/gdcs/production27/d1140/483394d2-8ce9-416a-b8e6-cb31f5f851d3.jpg?impolicy=fcrop&w=800&h=533&q=medium",
        text = "Beautiful cityscape!"
    ),
    PostCard(
        id = "2",
        place = LatLng(-2.7093, 45.35),
        imageUrl = "data:image/jpeg;base64,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",
        text = "City that never sleeps!"
    ),
    PostCard(
        id = "3",
        place = LatLng(5.3093, 143.0235),
        imageUrl = "https://www.andtrip.jp/images/b92f20d16e2c9ddc6376edb4ce5b5c99ee64bb94.jpg",
        text = "Eiffel Tower is stunning!"
    )
)
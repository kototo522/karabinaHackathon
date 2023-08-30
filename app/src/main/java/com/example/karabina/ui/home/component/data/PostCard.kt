package com.example.karabina.ui.home.component.data

data class PostCard(
    val id: String?,
    val location: String?,
    val imageUrl: String?,
    val text: String?,
)

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
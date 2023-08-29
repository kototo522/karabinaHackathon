package com.example.karabina.ui.home.component

import android.R
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(setShowDialog: (Boolean) -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    var text by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(5.dp),
            color = Color.White,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Column(modifier = Modifier.padding(20.dp).fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "投稿",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "キャンセル",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .clickable { setShowDialog(false) },
                        )
                    }
                    if(imageUri != null){
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "My Image",
                            modifier = Modifier.height(240.dp).padding(10.dp),
                        )
                    }else{
                        Button(onClick = { launcher.launch("image/*") }) {
                            Text(text = "画像を追加する")
                        }
                    }
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.padding(5.dp)
                    )
                    Button(
                        onClick = { setShowDialog(false) },
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth(),
                        ) {
                            Text(text = "投稿する",style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold))
                    }
                }

            }
        }
    }
}

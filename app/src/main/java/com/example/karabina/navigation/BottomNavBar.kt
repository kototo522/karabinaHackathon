package com.example.karabina.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.karabina.ui.camera.CameraScreen
import com.example.karabina.ui.home.HomeScreen
import com.example.karabina.ui.setting.PostScreen

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(route = "home", title = "Home", icon = Icons.Default.Place)
    object Post : BottomBarScreen(route = "post", title = "Post", icon = Icons.Default.LibraryBooks)
    object Camera : BottomBarScreen(route = "camera", title = "Camera", icon = Icons.Default.CameraAlt)
}

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val screenItems = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Post,
        BottomBarScreen.Camera,
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navStackBackEntry?.destination?.route

    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = BottomBarScreen.Home.route
            ) {
                composable(route = BottomBarScreen.Home.route) {
                    HomeScreen()
                }
                composable(route = BottomBarScreen.Post.route) {
                    PostScreen()
                }
                composable(route = BottomBarScreen.Camera.route) {
                    CameraScreen()
                }
            }
            NavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.background),
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                screenItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                        label = { Text(text = screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}



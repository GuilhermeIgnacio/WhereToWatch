package com.guilherme.wheretowatch.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.guilherme.wheretowatch.presentation.components.BottomNavigationBar
import com.guilherme.wheretowatch.presentation.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { _ ->

        NavHost(
            navController = navController,
            startDestination = HomeScreen
        ) {
            composable<HomeScreen> {
                HomeScreen()
            }

            composable<BookmarkedMoviesScreen> {
                Text("Bookmarked Movies Screen")
            }

        }
    }

}

@Serializable
object HomeScreen

@Serializable
object BookmarkedMoviesScreen

data class BottomNavigationItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
    val onClick: () -> Unit
)
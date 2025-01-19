package com.guilherme.wheretowatch.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.guilherme.wheretowatch.presentation.screen.HomeScreen
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

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route

    val items = listOf(
        BottomNavigationItem(
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "com.guilherme.wheretowatch.navigation.HomeScreen",
            onClick = { navController.navigate(HomeScreen) }
        ),
        BottomNavigationItem(
            label = "Bookmarked Movies",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            route = "com.guilherme.wheretowatch.navigation.BookmarkedMoviesScreen",
            onClick = { navController.navigate(BookmarkedMoviesScreen) }
        )
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (route == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                alwaysShowLabel = false,
                label = { Text(item.label) },
                selected = route == item.route,
                onClick = item.onClick
            )
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
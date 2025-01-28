package com.guilherme.wheretowatch.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.guilherme.wheretowatch.navigation.BookmarksScreen
import com.guilherme.wheretowatch.navigation.BottomNavigationItem
import com.guilherme.wheretowatch.navigation.HomeScreen

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route
    println("Route -> $route")

    val items = listOf(
        BottomNavigationItem(
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "com.guilherme.wheretowatch.navigation.HomeScreen",
            onClick = { navController.navigate(HomeScreen) }
        ),
        BottomNavigationItem(
            label = "Bookmarks",
            selectedIcon = Icons.Filled.Bookmark,
            unselectedIcon = Icons.Outlined.BookmarkBorder,
            route = "com.guilherme.wheretowatch.navigation.BookmarksScreen",
            onClick = { navController.navigate(BookmarksScreen) }
        )
    )

    AnimatedVisibility(
        visible = route == items[0].route || route == items[1].route,
        enter = fadeIn(animationSpec = tween(delayMillis = 200)) +
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(delayMillis = 200)
                ),
        exit = fadeOut(animationSpec = tween(delayMillis = 200)) +
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(delayMillis = 200)
                )

    ) {
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


}
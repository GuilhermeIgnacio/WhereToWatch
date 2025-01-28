package com.guilherme.wheretowatch.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.guilherme.wheretowatch.presentation.components.BottomNavigationBar
import com.guilherme.wheretowatch.presentation.screen.bookmarks.BookmarksScreen
import com.guilherme.wheretowatch.presentation.screen.home.HomeScreen
import com.guilherme.wheretowatch.presentation.screen.moviedetails.MovieDetailsScreen
import com.guilherme.wheretowatch.presentation.screen.tvshowdetails.TVShowDetailsScreen
import kotlinx.serialization.Serializable

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->

        NavHost(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = HomeScreen
        ) {
            composable<HomeScreen> {
                HomeScreen(
                    onMovieClick = { navController.navigate(MovieDetailsScreen(id = it)) },
                    onTvShowClicked = { navController.navigate(TvShowDetailsScreen(id = it)) }
                )
            }

            composable<BookmarksScreen> {
                BookmarksScreen(
                    onMovieClick = { navController.navigate(MovieDetailsScreen(it)) },
                    onTvShowClicked = { navController.navigate(TvShowDetailsScreen(it)) }
                )

            }

            composable<MovieDetailsScreen> {
                val args = it.toRoute<MovieDetailsScreen>()
                MovieDetailsScreen(
                    movieId = args.id,
                    onReturnNavigateButtonClicked = dropUnlessResumed { navController.popBackStack() }
                )
            }

            composable<TvShowDetailsScreen> {
                val args = it.toRoute<TvShowDetailsScreen>()
                TVShowDetailsScreen(
                    tvShowId = args.id,
                    onReturnButtonClicked = dropUnlessResumed {  navController.popBackStack() }
                )
            }

        }
    }

}

@Serializable
object HomeScreen

@Serializable
object BookmarksScreen

@Serializable
data class MovieDetailsScreen(val id: Int)

@Serializable
data class TvShowDetailsScreen(val id: Int)

data class BottomNavigationItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
    val onClick: () -> Unit,
)
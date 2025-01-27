package com.guilherme.wheretowatch.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.guilherme.wheretowatch.presentation.components.BottomNavigationBar
import com.guilherme.wheretowatch.presentation.screen.bookmarks.BookmarksScreen
import com.guilherme.wheretowatch.presentation.screen.home.HomeScreen
import com.guilherme.wheretowatch.presentation.screen.moviedetails.MovieDetailsScreen
import com.guilherme.wheretowatch.presentation.screen.tvshowdetails.TVShowDetailsScreen
import com.guilherme.wheretowatch.presentation.viewmodel.BookmarksViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
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
                HomeScreen(
                    onMovieClick = { navController.navigate(MovieDetailsScreen(id = it)) },
                    onTvShowClicked = { navController.navigate(TvShowDetailsScreen(id = it)) }
                )
            }

            composable<BookmarkedMoviesScreen> {
                BookmarksScreen(
                    onMovieClick = { navController.navigate(MovieDetailsScreen(it)) },
                    onTvShowClicked = { navController.navigate(TvShowDetailsScreen(it)) }
                )

            }

            composable<MovieDetailsScreen> {
                val args = it.toRoute<MovieDetailsScreen>()
                MovieDetailsScreen(
                    movieId = args.id,
                    onReturnNavigateButtonClicked = { navController.navigate(HomeScreen) }
                )
            }

            composable<TvShowDetailsScreen> {
                val args = it.toRoute<TvShowDetailsScreen>()
                TVShowDetailsScreen(
                    tvShowId = args.id
                )
            }

        }
    }

}

@Serializable
object HomeScreen

@Serializable
object BookmarkedMoviesScreen

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
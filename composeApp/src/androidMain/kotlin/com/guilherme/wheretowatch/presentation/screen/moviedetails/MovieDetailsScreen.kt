package com.guilherme.wheretowatch.presentation.screen.moviedetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.guilherme.wheretowatch.domain.model.MovieData

@Composable
fun MovieDetailsScreen(
    movieId: Int
) {

    Text(text = movieId.toString())

}
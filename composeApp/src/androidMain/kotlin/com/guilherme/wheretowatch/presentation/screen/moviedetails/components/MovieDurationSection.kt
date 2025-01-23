package com.guilherme.wheretowatch.presentation.screen.moviedetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse

@Composable
fun MovieDurationSection(movie: MovieDetailsResponse) {
    Column {
        Text(
            text = stringResource(R.string.duration_time),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        Text(
            text = stringResource(R.string.minutes, movie.runtime),
            fontWeight = FontWeight.ExtraLight,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}
package com.guilherme.wheretowatch.presentation.screen.moviedetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse
import org.jetbrains.compose.resources.stringResource
import wheretowatch.composeapp.generated.resources.Res
import wheretowatch.composeapp.generated.resources.duration_time
import wheretowatch.composeapp.generated.resources.minutes

@Composable
fun MovieDurationSection(movie: MovieDetailsResponse) {
    Column {
        Text(
            text = stringResource(Res.string.duration_time),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        Text(
            text = stringResource(Res.string.minutes, movie.runtime),
            fontWeight = FontWeight.ExtraLight,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}
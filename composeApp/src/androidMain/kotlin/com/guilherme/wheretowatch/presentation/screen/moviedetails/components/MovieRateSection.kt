package com.guilherme.wheretowatch.presentation.screen.moviedetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse

@Composable
fun MovieRateSection(movie: MovieDetailsResponse) {
    Column {

        val formattedVoteAverage = "%.1f".format(movie.voteAverage)

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "$formattedVoteAverage ⭐",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        Text(
            text = stringResource(R.string.vote_average, movie.voteCount),
            fontWeight = FontWeight.ExtraLight,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}
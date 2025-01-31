package com.guilherme.wheretowatch.presentation.screen.moviedetails.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse
import org.jetbrains.compose.resources.stringResource
import wheretowatch.composeapp.generated.resources.Res
import wheretowatch.composeapp.generated.resources.release_date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun MovieReleaseDateSection(movie: MovieDetailsResponse) {
    Column {

        val date = LocalDate.parse(movie.releaseDate)

        val locale = Locale.current.platformLocale

        val pattern = when (locale.country) {
            "US" -> "MM/dd/yyyy"
            else -> "dd/MM/yyyy"
        }

        val formatter = DateTimeFormatter.ofPattern(pattern)

        val formattedDate = date.format(formatter)

        Text(
            text = stringResource(Res.string.release_date),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        Text(
            text = formattedDate,
            fontWeight = FontWeight.ExtraLight,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}
package com.guilherme.wheretowatch.presentation.screen.moviedetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse
import com.guilherme.wheretowatch.presentation.screen.moviedetails.components.MovieDurationSection
import com.guilherme.wheretowatch.presentation.screen.moviedetails.components.MovieRateSection
import com.guilherme.wheretowatch.presentation.screen.moviedetails.components.MovieReleaseDateSection
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun MovieDetailsScreen(
    movieId: Int,
) {

    val viewModel = koinViewModel<MovieDetailsViewModel>()
    LaunchedEffect(Unit) { viewModel.fetchMovieDetails(movieId) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val verticalScroll = rememberScrollState()

    val movieDetails = state.movieDetails

    movieDetails?.let { movie ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(verticalScroll)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 24.dp,
                                bottomEnd = 24.dp
                            )
                        ),
                    contentScale = ContentScale.FillWidth,
                    model = "https://image.tmdb.org/t/p/original" + movie.posterPath,
                    contentDescription = "${movie.title} Poster",
                )

                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding(),
                    onClick = { /* Todo: Navigate back to home screen */ }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.return_button_content_description)
                    )
                }

                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .statusBarsPadding(),
                    onClick = { /* Todo: Bookmark movie */ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Bookmark,
                        contentDescription = stringResource(R.string.return_button_content_description)
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MovieReleaseDateSection(movie)

                MovieRateSection(movie)

                MovieDurationSection(movie)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(R.string.overview),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = movie.overview
            )

        }
    }

}
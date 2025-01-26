package com.guilherme.wheretowatch.presentation.screen.tvshowdetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.presentation.components.WatchProvidersSection
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun TVShowDetailsScreen(
    tvShowId: Int
) {

    val viewModel = koinViewModel<TvShowDetailsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchTvShowDetails(tvShowId)
        viewModel.fetchTvShowWatchProviders(tvShowId)
    }

    val tvShowDetails = state.tvShowDetails
    val tvShowWatchProviders = state.tvShowWatchProviders

    val verticalScroll = rememberScrollState()

    tvShowDetails?.let { tvShow ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
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
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/original" + tvShow.posterPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "${tvShow.name} Poster",
                    placeholder = painterResource(R.drawable.placeholder_image),
                    error = painterResource(R.drawable.placeholder_image)
                )

                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding(),
                    onClick = { /*Todo: Pop back stack or navigate to home screen*/ }
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
                    onClick = { /* Todo: Bookmark tv show */ }
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
                text = tvShow.name,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Column {

                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val firstAirYear = LocalDate.parse(tvShow.firstAirDate, formatter)
                    val lastAirYear = LocalDate.parse(tvShow.lastAirDate, formatter)


                    Text(
                        text = stringResource(R.string.release_date),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                    Text(
                        text = "${firstAirYear.year} - ${lastAirYear.year}",
                        fontWeight = FontWeight.ExtraLight,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                }

                Column {
                    val formattedVoteAverage = "%.1f".format(tvShow.voteAverage)

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "$formattedVoteAverage ⭐",
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                    Text(
                        text = stringResource(R.string.vote_average, tvShow.voteCount),
                        fontWeight = FontWeight.ExtraLight,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                }

                Column {
                    Text(
                        text = stringResource(R.string.seasons, tvShow.numberOfSeasons),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                    Text(
                        text = stringResource(R.string.episodes, tvShow.numberOfEpisodes),
                        fontWeight = FontWeight.ExtraLight,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                }

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
                text = tvShow.overview
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (tvShowWatchProviders != null) {

                /*Todo: Add JustWatch credits*/
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(R.string.where_to_watch),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )

                Spacer(modifier = Modifier.height(16.dp))

                val flatRate = tvShowWatchProviders.flatrate
                WatchProvidersSection(providerLabel = stringResource(R.string.watch_providers_label_subscription), provider = flatRate)

                val buy = tvShowWatchProviders.buy
                WatchProvidersSection(providerLabel = stringResource(R.string.watch_provider_label_buy), provider = buy)

                val rent = tvShowWatchProviders.rent
                WatchProvidersSection(providerLabel = stringResource(R.string.watch_provider_label_rent), provider = rent)

                val ads = tvShowWatchProviders.ads
                WatchProvidersSection(providerLabel = "Ads", provider = ads)


            }

        }
    }

}
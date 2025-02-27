package com.guilherme.wheretowatch.presentation.screen.tvshowdetails

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.data.toMovieData
import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.presentation.components.AdvertView
import com.guilherme.wheretowatch.presentation.components.ErrorDisplay
import com.guilherme.wheretowatch.presentation.components.LoadingIcon
import com.guilherme.wheretowatch.presentation.components.WatchProvidersSection
import com.guilherme.wheretowatch.presentation.components.WhereToWatchHeader
import com.guilherme.wheretowatch.presentation.viewmodel.TvShowDetailsEvents
import com.guilherme.wheretowatch.presentation.viewmodel.TvShowDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import wheretowatch.composeapp.generated.resources.Res
import wheretowatch.composeapp.generated.resources.bookmark_movie_content_description
import wheretowatch.composeapp.generated.resources.episodes
import wheretowatch.composeapp.generated.resources.overview
import wheretowatch.composeapp.generated.resources.release_date
import wheretowatch.composeapp.generated.resources.remove_bookmark_content_description
import wheretowatch.composeapp.generated.resources.return_button_content_description
import wheretowatch.composeapp.generated.resources.seasons
import wheretowatch.composeapp.generated.resources.vote_average
import wheretowatch.composeapp.generated.resources.watch_provider_label_buy
import wheretowatch.composeapp.generated.resources.watch_provider_label_rent
import wheretowatch.composeapp.generated.resources.watch_providers_error_snackbar_message
import wheretowatch.composeapp.generated.resources.watch_providers_label_subscription
import wheretowatch.composeapp.generated.resources.watch_with_ads
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun TVShowDetailsScreen(
    tvShowId: Int,
    onReturnButtonClicked: () -> Unit,
) {

    val viewModel = koinViewModel<TvShowDetailsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    LaunchedEffect(tvShowId) {
        viewModel.fetchTvShowData(tvShowId)
    }

    val tvShowDetails = state.tvShowDetails
    val tvShowWatchProviders = state.tvShowWatchProviders

    val verticalScroll = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    AnimatedContent(
        targetState = state.isTvShowDetailsLoading && state.isTvShowWatchProvidersLoading && state.isBookmarkedTvShowsLoading,
        label = ""
    ) {

        if (it) {
            LoadingIcon()
        }

        if (!it && state.isError == false) {
            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { _ ->
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
                                onClick = { onReturnButtonClicked() }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(Res.string.return_button_content_description)
                                )
                            }

                            IconButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .statusBarsPadding(),
                                onClick = {
                                    onEvent(TvShowDetailsEvents.BookmarkTvShow(tvShow.toMovieData()))
                                }
                            ) {
                                Crossfade(
                                    targetState = tvShow.toMovieData() in state.bookmarkedTvShows,
                                    label = ""
                                ) {
                                    Icon(
                                        imageVector = if (it) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                        contentDescription = if (it)
                                            stringResource(Res.string.remove_bookmark_content_description)
                                        else
                                            stringResource(Res.string.bookmark_movie_content_description)
                                    )
                                }
                            }

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = tvShow.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            textAlign = TextAlign.Center,
                            lineHeight = MaterialTheme.typography.headlineLarge.lineHeight
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            if (tvShow.firstAirDate.isNotBlank() && tvShow.lastAirDate.isNotBlank()) {
                                Column {

                                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                    val firstAirYear =
                                        LocalDate.parse(tvShow.firstAirDate, formatter)
                                    val lastAirYear = LocalDate.parse(tvShow.lastAirDate, formatter)

                                    Text(
                                        text = stringResource(Res.string.release_date),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                    )

                                    Text(
                                        text = "${firstAirYear.year} - ${lastAirYear.year}",
                                        fontWeight = FontWeight.ExtraLight,
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                    )

                                }
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
                                    text = stringResource(
                                        Res.string.vote_average,
                                        tvShow.voteCount
                                    ),
                                    fontWeight = FontWeight.ExtraLight,
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                )
                            }

                            Column {
                                Text(
                                    text = stringResource(
                                        Res.string.seasons,
                                        tvShow.numberOfSeasons
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                )

                                Text(
                                    text = stringResource(
                                        Res.string.episodes,
                                        tvShow.numberOfEpisodes
                                    ),
                                    fontWeight = FontWeight.ExtraLight,
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (tvShow.overview.isNotBlank()) {
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = stringResource(Res.string.overview),
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                        }

                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = tvShow.overview
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        val providers = listOf(
                            tvShowWatchProviders?.flatrate,
                            tvShowWatchProviders?.rent,
                            tvShowWatchProviders?.buy,
                            tvShowWatchProviders?.ads
                        )

                        val hasProviders = providers.any { it?.isNotEmpty() == true }

                        if (tvShowWatchProviders != null && hasProviders) {

                            WhereToWatchHeader()

                            Spacer(modifier = Modifier.height(16.dp))

                            val flatRate = tvShowWatchProviders.flatrate
                            WatchProvidersSection(
                                providerLabel = stringResource(Res.string.watch_providers_label_subscription),
                                provider = flatRate
                            )

                            val buy = tvShowWatchProviders.buy
                            WatchProvidersSection(
                                providerLabel = stringResource(Res.string.watch_provider_label_buy),
                                provider = buy
                            )

                            val rent = tvShowWatchProviders.rent
                            WatchProvidersSection(
                                providerLabel = stringResource(Res.string.watch_provider_label_rent),
                                provider = rent
                            )

                            val ads = tvShowWatchProviders.ads
                            WatchProvidersSection(
                                providerLabel = stringResource(Res.string.watch_with_ads),
                                provider = ads
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                        } else {

                            val coroutineScope = rememberCoroutineScope()

                            when (state.fetchWatchProvidersError) {
                                ResponseError.BAD_REQUEST -> triggerSnackBar(
                                    snackbarHostState,
                                    coroutineScope
                                )

                                ResponseError.UNAUTHORIZED -> triggerSnackBar(
                                    snackbarHostState,
                                    coroutineScope
                                )

                                ResponseError.FORBIDDEN -> triggerSnackBar(
                                    snackbarHostState,
                                    coroutineScope
                                )

                                ResponseError.NOT_FOUND -> triggerSnackBar(
                                    snackbarHostState,
                                    coroutineScope
                                )

                                ResponseError.METHOD_NOT_ALLOWED -> triggerSnackBar(
                                    snackbarHostState,
                                    coroutineScope
                                )

                                ResponseError.REQUEST_TIMEOUT -> triggerSnackBar(
                                    snackbarHostState,
                                    coroutineScope
                                )

                                ResponseError.TOO_MANY_REQUESTS -> triggerSnackBar(
                                    snackbarHostState,
                                    coroutineScope
                                )

                                ResponseError.NULL_VALUE -> { /*No Watch Providers*/
                                }

                                ResponseError.UNRESOLVED_ADDRESS -> triggerSnackBar(
                                    snackbarHostState, coroutineScope
                                )

                                ResponseError.UNKNOWN -> triggerSnackBar(
                                    snackbarHostState, coroutineScope
                                )

                                null -> {}
                            }

                        }

                        AdvertView()

                    }
                }
            }
        } else if (!it && state.isError == true) {
            Box {

                IconButton(
                    modifier = Modifier.statusBarsPadding(),
                    onClick = { onReturnButtonClicked() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.return_button_content_description)
                    )
                }
                ErrorDisplay(state.error)
            }
        }
    }

}

private fun triggerSnackBar(snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        snackbarHostState.showSnackbar(getString(Res.string.watch_providers_error_snackbar_message))
    }

}
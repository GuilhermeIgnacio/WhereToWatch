package com.guilherme.wheretowatch.presentation.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.domain.MediaType
import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.presentation.viewmodel.HomeEvents
import com.guilherme.wheretowatch.presentation.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import wheretowatch.composeapp.generated.resources.Res
import wheretowatch.composeapp.generated.resources.error_image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onTvShowClicked: (Int) -> Unit,
) {

    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    var expanded by rememberSaveable { mutableStateOf(false) }

    if (!state.isError) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    inputField = {
                        SearchBarDefaults.InputField(
                            onSearch = { onEvent(HomeEvents.OnSearch) },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text(stringResource(R.string.search_placeholder)) },
                            leadingIcon = {

                                AnimatedContent(
                                    targetState = state.searchMode
                                ) {
                                    if (it) {
                                        IconButton(
                                            onClick = { onEvent(HomeEvents.DisableSearchMode) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Return Icon Button"
                                            )
                                        }
                                    } else {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = "Search Icon"
                                        )
                                    }
                                }
                            },
                            query = state.searchQuery,
                            onQueryChange = { onEvent(HomeEvents.OnQueryChange(it)) }
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    expanded = false,
                    onExpandedChange = {}
                ) {}
            }

            item {

                AnimatedContent(
                    targetState = if (!state.searchMode) stringResource(R.string.popular_movies) else "Results for \"${state.inputedSearchQuery ?: ""}\""
                ) { text ->
                    Text(
                        text = text,
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }

            val apiResponse = if (!state.searchMode) state.apiResponse else state.searchResults

            items(apiResponse.chunked(2)) { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { movie ->
                        AsyncImage(
                            modifier = Modifier
                                .width(240.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable(onClick = dropUnlessResumed {
                                    when (movie.mediaType) {
                                        MediaType.MOVIE.value -> onMovieClick(movie.id)
                                        MediaType.TV.value -> onTvShowClicked(movie.id)
                                        null -> onMovieClick(movie.id)
                                    }
                                }),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                                .crossfade(true)
                                .build(),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                            placeholder = painterResource(R.drawable.placeholder_image),
                            error = painterResource(R.drawable.placeholder_image)
                        )
                    }

                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

        }

    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.fillMaxWidth(),
                imageVector = vectorResource(Res.drawable.error_image),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )

            when (state.error) {
                ResponseError.BAD_REQUEST -> {
                    ErrorMessage(
                        message = "We couldn't process the request. Please restart and try again."
                    )
                }

                ResponseError.UNAUTHORIZED -> {
                    ErrorMessage(
                        message = "The API returned an error (Unauthorized). Please restart the app and try again. If the issue persists, contact support."
                    )

                }

                ResponseError.FORBIDDEN -> {
                    ErrorMessage(
                        message = "The API returned an error (Forbidden). Please restart the app and try again. If the issue persists, contact support."
                    )

                }

                ResponseError.NOT_FOUND -> {
                    ErrorMessage(message = "Content not found. If the issue persists, please contact support.")
                }

                ResponseError.METHOD_NOT_ALLOWED -> {
                    ErrorMessage(
                        message = "The API returned an error (Method not allowed). Please restart the app and try again. If the issue persists, contact support."
                    )
                }

                ResponseError.REQUEST_TIMEOUT -> {
                    ErrorMessage(
                        message = "The request took too long to respond. Please check your connection and try again."
                    )
                }

                ResponseError.TOO_MANY_REQUESTS -> {
                    ErrorMessage(message = "You're sending requests too quickly! Please wait a moment and try again.")

                }

                ResponseError.NULL_VALUE -> {
                    ErrorMessage(
                        message = "The API returned null values. Please restart the app and try again. If the issue persists, contact support."
                    )
                }

                ResponseError.UNRESOLVED_ADDRESS -> {
                    ErrorMessage(message = "It looks like you're offline. Please check your internet connection and try again.")
                }

                ResponseError.UNKNOWN -> {

                    ErrorMessage(
                        message = "An unknown error occurred. Please restart the app and try again"
                    )
                }

                null -> {
                    ErrorMessage(message = "")
                }

            }

        }
    }

}

@Composable
private fun ErrorMessage(message: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Oops! Something Went Wrong",
        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        lineHeight = MaterialTheme.typography.headlineLarge.lineHeight
    )

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = message,
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}

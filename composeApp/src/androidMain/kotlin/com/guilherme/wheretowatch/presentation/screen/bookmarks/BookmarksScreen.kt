package com.guilherme.wheretowatch.presentation.screen.bookmarks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.domain.MediaType
import com.guilherme.wheretowatch.presentation.viewmodel.BookmarksViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookmarksScreen(
    onMovieClick: (Int) -> Unit,
    onTvShowClicked: (Int) -> Unit,
) {

    val viewModel = koinViewModel<BookmarksViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {

            Text(
                text = stringResource(R.string.bookmarks),
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold
            )

        }

        item {
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        val bookmarks = state.bookmarks

        items(bookmarks.chunked(2)) { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { media ->
                    AsyncImage(
                        modifier = Modifier
                            .width(240.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable(onClick = dropUnlessResumed {
                                when (media.mediaType) {
                                    MediaType.MOVIE.value -> onMovieClick(media.id)
                                    MediaType.TV.value -> onTvShowClicked(media.id)
                                    null -> onMovieClick(media.id)
                                }
                            }),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w500" + media.posterPath)
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

}
package com.guilherme.wheretowatch.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.guilherme.wheretowatch.R
import com.guilherme.wheretowatch.domain.model.Provider

@Composable
fun WatchProvidersSection(providerLabel: String, provider: List<Provider>?) {
    if (provider != null) {

        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = providerLabel,
            fontSize = MaterialTheme.typography.labelLarge.fontSize
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(provider) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(70.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/original" + it.logoPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = it.providerName + "Logo",
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(R.drawable.placeholder_image),
                    error = painterResource(R.drawable.placeholder_image)
                )
            }
        }

    }
}
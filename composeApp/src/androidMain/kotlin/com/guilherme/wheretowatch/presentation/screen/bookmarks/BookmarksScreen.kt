package com.guilherme.wheretowatch.presentation.screen.bookmarks

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.guilherme.wheretowatch.presentation.viewmodel.BookmarksViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookmarksScreen() {

    val viewModel = koinViewModel<BookmarksViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn {
        items(state.bookmarks) {
            Text(it.posterPath.toString())
        }
    }

}
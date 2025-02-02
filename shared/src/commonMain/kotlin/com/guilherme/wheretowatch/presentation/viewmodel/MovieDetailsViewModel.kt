package com.guilherme.wheretowatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.wheretowatch.data.local.LocalDatabase
import com.guilherme.wheretowatch.domain.MediaType
import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.domain.Result
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.domain.model.Country
import com.guilherme.wheretowatch.domain.model.MediaData
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieDetailsState(
    val bookmarkedMovies: List<MediaData> = emptyList(),
    val movieDetails: MovieDetailsResponse? = null,
    val movieWatchProviders: Country? = null,
    val isError: Boolean? = null,
    val error: ResponseError? = null,
    val fetchWatchProvidersError: ResponseError? = null
)

sealed interface MovieDetailsEvents {
    data class BookmarkMovie(val value: MediaData) : MovieDetailsEvents
}

class MovieDetailsViewModel(
    private val tmdbApiService: TheMovieDatabaseApiService,
    private val localDatabase: LocalDatabase,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fetchBookmarkedMovies()
        }
    }

    suspend fun fetchMovieDetails(id: Int) {
        when (val result = tmdbApiService.fetchMovieDetails(id)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        movieDetails = result.data,
                        isError = false,
                        error = null
                    )
                }
            }

            is Result.Error -> {
                _state.update {
                    it.copy(
                        isError = true,
                        error = result.error
                    )
                }
            }
        }
    }

    suspend fun fetchMovieWatchProviders(id: Int) {
        when (val result = tmdbApiService.fetchMovieProviders(id)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        movieWatchProviders = result.data,
                        fetchWatchProvidersError = null
                    )
                }
            }

            is Result.Error -> {
                _state.update {
                    it.copy(
                        fetchWatchProvidersError = result.error
                    )
                }
            }
        }

    }

    private suspend fun fetchBookmarkedMovies() {
        localDatabase.getBookmarks()
            .map {
                it.filter {
                    it.mediaType == MediaType.MOVIE.value
                }
            }
            .collect { list ->
                _state.update {
                    it.copy(
                        bookmarkedMovies = list
                    )
                }
            }
    }

    fun onEvent(event: MovieDetailsEvents) {
        when (event) {
            is MovieDetailsEvents.BookmarkMovie -> {
                viewModelScope.launch {
                    val movie = event.value

                    if (movie in _state.value.bookmarkedMovies) {
                        //Remove from bookmarks
                        localDatabase.delete(event.value)
                    } else {
                        //Insert into bookmarks
                        localDatabase.insert(event.value)
                    }
                }
            }
        }
    }

}
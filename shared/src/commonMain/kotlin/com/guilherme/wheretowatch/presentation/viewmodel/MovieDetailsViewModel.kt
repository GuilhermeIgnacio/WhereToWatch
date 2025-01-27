package com.guilherme.wheretowatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.wheretowatch.data.local.LocalDatabase
import com.guilherme.wheretowatch.domain.MediaType
import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.domain.Result
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.domain.model.Country
import com.guilherme.wheretowatch.domain.model.MovieData
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieDetailsState(
    val bookmarkedMovies: List<MovieData> = emptyList(),
    val movieDetails: MovieDetailsResponse? = null,
    val movieWatchProviders: Country? = null,
)

sealed interface MovieDetailsEvents {
    data class BookmarkMovie(val value: MovieData) : MovieDetailsEvents
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
                _state.update { it.copy(movieDetails = result.data) }
            }

            is Result.Error -> TODO()
        }
    }

    suspend fun fetchMovieWatchProviders(id: Int) {
        when (val result = tmdbApiService.fetchMovieProviders(id)) {
            is Result.Success -> {
                _state.update { it.copy(movieWatchProviders = result.data) }
            }

            is Result.Error -> {
                when (result.error) {
                    ResponseError.BAD_REQUEST -> TODO()
                    ResponseError.UNAUTHORIZED -> TODO()
                    ResponseError.FORBIDDEN -> TODO()
                    ResponseError.NOT_FOUND -> TODO()
                    ResponseError.METHOD_NOT_ALLOWED -> TODO()
                    ResponseError.REQUEST_TIMEOUT -> TODO()
                    ResponseError.TOO_MANY_REQUESTS -> TODO()
                    ResponseError.NULL_VALUE -> {}
                    ResponseError.UNKNOWN -> TODO()
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
                        //Todo: Delete from db
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
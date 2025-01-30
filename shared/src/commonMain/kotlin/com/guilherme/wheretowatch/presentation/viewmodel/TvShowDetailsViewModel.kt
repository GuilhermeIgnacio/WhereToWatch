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
import com.guilherme.wheretowatch.domain.model.TvShowDetailsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TvShowDetailsState(
    val bookmarkedTvShows: List<MediaData> = emptyList(),
    val tvShowDetails: TvShowDetailsResponse? = null,
    val tvShowWatchProviders: Country? = null,
)

sealed interface TvShowDetailsEvents {
    data class BookmarkTvShow(val value: MediaData) : TvShowDetailsEvents
}

class TvShowDetailsViewModel(
    private val tmdbApiService: TheMovieDatabaseApiService,
    private val localDatabase: LocalDatabase,
) : ViewModel() {

    private val _state = MutableStateFlow(TvShowDetailsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fetchBookmarkedTvShows()
        }
    }

    suspend fun fetchTvShowDetails(id: Int) {
        when (val result = tmdbApiService.fetchTvShowDetails(id)) {
            is Result.Success -> {
                _state.update { it.copy(tvShowDetails = result.data) }
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
                    ResponseError.NULL_VALUE -> TODO()
                    ResponseError.UNKNOWN -> TODO()
                    ResponseError.UNRESOLVED_ADDRESS -> TODO()
                }
            }
        }
    }

    suspend fun fetchTvShowWatchProviders(id: Int) {
        when (val result = tmdbApiService.fetchTvShowWatchProviders(id)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        tvShowWatchProviders = result.data
                    )
                }
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
                    ResponseError.NULL_VALUE -> TODO()
                    ResponseError.UNKNOWN -> TODO()
                    ResponseError.UNRESOLVED_ADDRESS -> TODO()
                }
            }
        }
    }

    private suspend fun fetchBookmarkedTvShows() {
        localDatabase.getBookmarks()
            .map {
                it.filter {
                    it.mediaType == MediaType.TV.value
                }
            }
            .collect { list ->
                _state.update {
                    it.copy(
                        bookmarkedTvShows = list
                    )
                }
            }
    }

    fun onEvent(event: TvShowDetailsEvents) {
        when (event) {
            is TvShowDetailsEvents.BookmarkTvShow -> {
                viewModelScope.launch {
                    val tvShow = event.value

                    if (tvShow in _state.value.bookmarkedTvShows) {
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
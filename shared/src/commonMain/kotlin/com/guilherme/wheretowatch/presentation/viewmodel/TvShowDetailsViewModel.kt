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
    val fetchWatchProvidersError: ResponseError? = null,
    val isError: Boolean? = null,
    val error: ResponseError? = null,
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
                _state.update {
                    it.copy(
                        tvShowDetails = result.data,
                        isError = false,
                        error = null
                    )
                }
            }

            is Result.Error -> {
                _state.update{it.copy(
                    isError = true,
                    error = result.error
                )}
            }
        }
    }

    suspend fun fetchTvShowWatchProviders(id: Int) {
        when (val result = tmdbApiService.fetchTvShowWatchProviders(id)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        tvShowWatchProviders = result.data,
                        fetchWatchProvidersError = null
                    )
                }
            }

            is Result.Error -> {
                _state.update { it.copy(fetchWatchProvidersError = result.error) }
                println(result.error.name)
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
package com.guilherme.wheretowatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.domain.Result
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.domain.model.MediaData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val isError: Boolean? = null,
    val error: ResponseError? = null,
    val apiResponse: List<MediaData> = emptyList(),
    val searchQuery: String = "",
    val inputedSearchQuery: String? = null,
    val searchResults: List<MediaData> = emptyList(),
    val searchMode: Boolean = false,
    val isLoading: Boolean = true,
)

sealed interface HomeEvents {
    data class OnQueryChange(val value: String) : HomeEvents
    data object OnSearch : HomeEvents
    data object DisableSearchMode : HomeEvents
}

class HomeViewModel(
    private val tmdbApiService: TheMovieDatabaseApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            when (val result = tmdbApiService.fetchMovies()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isError = false,
                            apiResponse = result.data.results
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
        }.invokeOnCompletion {
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.OnQueryChange -> {
                _state.update { it.copy(searchQuery = event.value) }
            }

            HomeEvents.OnSearch -> {
                viewModelScope.launch {
                    val searchQuery = _state.value.searchQuery.replace(Regex("\\s+"), " ").trim()

                    when (val result = tmdbApiService.search(searchQuery.replace(" ", "%20"))) {
                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    inputedSearchQuery = searchQuery,
                                    searchMode = true,
                                    isError = false,
                                    error = null,
                                    searchResults = result.data.results
                                )
                            }
                        }

                        is Result.Error -> {
                            _state.update {
                                it.copy(
                                    isError = true,
                                    error = result.error,
                                )
                            }
                        }
                    }


                }
            }

            HomeEvents.DisableSearchMode -> {
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        searchQuery = "",
                        inputedSearchQuery = null,
                        searchMode = false
                    )
                }
            }
        }
    }

}
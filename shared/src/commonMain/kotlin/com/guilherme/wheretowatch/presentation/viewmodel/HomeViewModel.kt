package com.guilherme.wheretowatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.domain.Result
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.domain.model.MovieData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val apiResponse: List<MovieData> = emptyList(),
    val searchQuery: String = "",
    val inputedSearchQuery: String? = null,
    val searchResults: List<MovieData> = emptyList(),
    val searchMode: Boolean = false,
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
                            apiResponse = result.data.results
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
                    }
                }
            }
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

                    when (val result = tmdbApiService.search(searchQuery.replace(" ","%20"))) {
                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    inputedSearchQuery = searchQuery,
                                    searchMode = true,
                                    searchResults = result.data.results
                                )
                            }
                        }

                        is Result.Error -> {
                            when(result.error) {
                                ResponseError.BAD_REQUEST -> TODO()
                                ResponseError.UNAUTHORIZED -> TODO()
                                ResponseError.FORBIDDEN -> TODO()
                                ResponseError.NOT_FOUND -> TODO()
                                ResponseError.METHOD_NOT_ALLOWED -> TODO()
                                ResponseError.REQUEST_TIMEOUT -> TODO()
                                ResponseError.TOO_MANY_REQUESTS -> TODO()
                                ResponseError.NULL_VALUE -> TODO()
                                ResponseError.UNKNOWN -> TODO()
                            }
                        }
                    }


                }
            }

            HomeEvents.DisableSearchMode -> {
                _state.update { it.copy(
                    searchResults = emptyList(),
                    searchQuery = "",
                    inputedSearchQuery = null,
                    searchMode = false
                ) }
            }
        }
    }

}
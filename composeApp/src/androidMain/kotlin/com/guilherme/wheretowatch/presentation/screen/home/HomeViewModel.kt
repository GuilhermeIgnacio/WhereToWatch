package com.guilherme.wheretowatch.presentation.screen.home

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
    val searchQuery: String = ""
)

sealed interface HomeEvents {
    data class OnQueryChange(val value: String): HomeEvents
}

class HomeViewModel(
    private val tmdbApiService: TheMovieDatabaseApiService
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
        when(event) {
            is HomeEvents.OnQueryChange -> {
                _state.update { it.copy(searchQuery = event.value) }
            }
        }
    }

}
package com.guilherme.wheretowatch.presentation.screen.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.wheretowatch.domain.Result
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieDetailsState(
    val movieDetails: MovieDetailsResponse? = null,
)

class MovieDetailsViewModel(
    private val tmdbApiService: TheMovieDatabaseApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsState())
    val state = _state.asStateFlow()

    suspend fun fetchMovieDetails(id: Int) {
        when(val result = tmdbApiService.fetchMovieDetails(id)) {
            is Result.Success -> {
                _state.update { it.copy(movieDetails = result.data) }
            }
            is Result.Error -> TODO()
        }
    }

}
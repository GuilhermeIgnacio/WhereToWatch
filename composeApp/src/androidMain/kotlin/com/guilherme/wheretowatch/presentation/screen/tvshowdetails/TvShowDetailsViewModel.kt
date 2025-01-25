package com.guilherme.wheretowatch.presentation.screen.tvshowdetails

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.domain.Result
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.domain.model.TvShowDetailsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TvShowDetailsState(
    val tvShowDetails: TvShowDetailsResponse? = null
)

class TvShowDetailsViewModel(private val tmdbApiService: TheMovieDatabaseApiService) : ViewModel() {

    private val _state = MutableStateFlow(TvShowDetailsState())
    val state = _state.asStateFlow()

    suspend fun fetchTvShowDetails(id: Int) {
        when(val result = tmdbApiService.fetchTvShowDetails(id)) {
            is Result.Success -> {
                _state.update { it.copy(tvShowDetails = result.data) }
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
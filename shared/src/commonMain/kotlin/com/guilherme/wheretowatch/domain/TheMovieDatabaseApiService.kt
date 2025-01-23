package com.guilherme.wheretowatch.domain

import com.guilherme.wheretowatch.domain.model.ApiResponse
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse

interface TheMovieDatabaseApiService {
    suspend fun fetchMovies(): Result<ApiResponse, ResponseError>
    suspend fun fetchMovieDetails(id: Int): Result<MovieDetailsResponse, ResponseError>
}
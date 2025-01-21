package com.guilherme.wheretowatch.domain

import com.guilherme.wheretowatch.domain.model.ApiResponse

interface TheMovieDatabaseApiService {
    suspend fun fetchMovies(): Result<ApiResponse, ResponseError>
}
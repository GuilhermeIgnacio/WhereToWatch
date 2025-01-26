package com.guilherme.wheretowatch.domain

import com.guilherme.wheretowatch.domain.model.ApiResponse
import com.guilherme.wheretowatch.domain.model.Country
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse
import com.guilherme.wheretowatch.domain.model.TvShowDetailsResponse

interface TheMovieDatabaseApiService {
    suspend fun fetchMovies(): Result<ApiResponse, ResponseError>
    suspend fun fetchMovieDetails(id: Int): Result<MovieDetailsResponse, ResponseError>
    suspend fun fetchMovieProviders(id: Int): Result<Country, ResponseError>
    suspend fun search(query: String): Result<ApiResponse, ResponseError>
    suspend fun fetchTvShowDetails(id: Int): Result<TvShowDetailsResponse, ResponseError>
    suspend fun fetchTvShowWatchProviders(id: Int): Result<Country, ResponseError>
}
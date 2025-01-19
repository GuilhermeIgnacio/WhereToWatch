package com.guilherme.wheretowatch.domain

interface TheMovieDatabaseApiService {
    suspend fun fetchMovies()
}
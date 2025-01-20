package com.guilherme.wheretowatch.data.remote.api

import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.domain.model.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TheMovieDatabaseApiServiceImpl : TheMovieDatabaseApiService {

    //Todo: DO NOT COMMIT THIS VAL
    companion object {
        const val API_KEY = ""
        const val POPULAR_MOVIES_ENDPOINT = "https://api.themoviedb.org/3/movie/popular"
    }

    override suspend fun fetchMovies(): ApiResponse? {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )

            }

            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(API_KEY, API_KEY)
                    }
                }
            }

        }

        return try {
            val response = client.get(POPULAR_MOVIES_ENDPOINT)
            response.body<ApiResponse>()

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }
}
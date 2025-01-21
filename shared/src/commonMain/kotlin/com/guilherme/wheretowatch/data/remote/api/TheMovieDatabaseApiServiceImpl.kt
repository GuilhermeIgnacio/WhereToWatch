package com.guilherme.wheretowatch.data.remote.api

import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.domain.Result
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
        const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NTJmMGIxYTY5MjdjMTVmZjEzZGVlMjEzMTFhZDlmZiIsIm5iZiI6MTY4MzY4MTMwMy45NzUwMDAxLCJzdWIiOiI2NDVhZjAxNzc3ZDIzYjAwZmNjYmQxNDciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.jEZ4zRmpGHuM5T-MjKdgWw15TI_nf4u21nqMTP9Ajrc"
        const val POPULAR_MOVIES_ENDPOINT = "https://api.themoviedb.org/3/movie/popular"
    }

    override suspend fun fetchMovies(): Result<ApiResponse, ResponseError> {
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

            when(response.status.value) {
                200 -> Result.Success(response.body<ApiResponse>())
                400 -> Result.Error(ResponseError.BAD_REQUEST)
                401 -> Result.Error(ResponseError.UNAUTHORIZED)
                403 -> Result.Error(ResponseError.FORBIDDEN)
                404 -> Result.Error(ResponseError.NOT_FOUND)
                405 -> Result.Error(ResponseError.METHOD_NOT_ALLOWED)
                408 -> Result.Error(ResponseError.REQUEST_TIMEOUT)
                429 -> Result.Error(ResponseError.TOO_MANY_REQUESTS)
                else -> {
                    println("Http Status Value -> ${response.status.value}")
                    Result.Error(ResponseError.UNKNOWN)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ResponseError.UNKNOWN)
        }

    }
}
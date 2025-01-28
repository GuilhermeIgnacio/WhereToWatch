package com.guilherme.wheretowatch.data.remote.api

import androidx.compose.ui.text.intl.Locale
import com.guilherme.wheretowatch.API_KEY
import com.guilherme.wheretowatch.domain.ResponseError
import com.guilherme.wheretowatch.domain.Result
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.domain.model.TvShowDetailsResponse
import com.guilherme.wheretowatch.domain.model.ApiResponse
import com.guilherme.wheretowatch.domain.model.Country
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse
import com.guilherme.wheretowatch.domain.model.MovieWatchProvidersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TheMovieDatabaseApiServiceImpl : TheMovieDatabaseApiService {

    companion object {
        val languageTag = Locale.current.toLanguageTag()
        val region = Locale.current.region
        const val POPULAR_MOVIES_ENDPOINT = "https://api.themoviedb.org/3/movie/popular?language="
        const val MOVIE_DETAILS_ENDPOINT = "https://api.themoviedb.org/3/movie/"
        const val MOVIE_WATCH_PROVIDERS_ENDPOINT = "https://api.themoviedb.org/3/movie/"
        const val SEARCH_ENDPOINT = "https://api.themoviedb.org/3/search/multi?"
        const val TV_SHOW_DETAILS_ENDPOINT = "https://api.themoviedb.org/3/tv/"
    }

    private val client = HttpClient(CIO) {
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

        install(HttpCache)
    }

    override suspend fun fetchMovies(): Result<ApiResponse, ResponseError> {

        return try {
            val response = client.get(POPULAR_MOVIES_ENDPOINT + languageTag)

            when (response.status.value) {
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

    override suspend fun fetchMovieDetails(id: Int): Result<MovieDetailsResponse, ResponseError> {

        return try {
            val response = client.get("${MOVIE_DETAILS_ENDPOINT}$id?language=$languageTag")

            when (response.status.value) {
                200 -> Result.Success(response.body<MovieDetailsResponse>())
                400 -> Result.Error(ResponseError.BAD_REQUEST)
                401 -> Result.Error(ResponseError.UNAUTHORIZED)
                403 -> Result.Error(ResponseError.FORBIDDEN)
                404 -> Result.Error(ResponseError.NOT_FOUND)
                405 -> Result.Error(ResponseError.METHOD_NOT_ALLOWED)
                408 -> Result.Error(ResponseError.REQUEST_TIMEOUT)
                429 -> Result.Error(ResponseError.TOO_MANY_REQUESTS)
                else -> {
                    println(response.status)
                    Result.Error(ResponseError.UNKNOWN)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ResponseError.UNKNOWN)
        }

    }

    override suspend fun fetchTvShowDetails(id: Int): Result<TvShowDetailsResponse, ResponseError> {

        return try {

            val response = client.get("$TV_SHOW_DETAILS_ENDPOINT$id?language=$languageTag")

            when (response.status.value) {
                200 -> Result.Success(response.body())
                400 -> Result.Error(ResponseError.BAD_REQUEST)
                401 -> Result.Error(ResponseError.UNAUTHORIZED)
                403 -> Result.Error(ResponseError.FORBIDDEN)
                404 -> Result.Error(ResponseError.NOT_FOUND)
                405 -> Result.Error(ResponseError.METHOD_NOT_ALLOWED)
                408 -> Result.Error(ResponseError.REQUEST_TIMEOUT)
                429 -> Result.Error(ResponseError.TOO_MANY_REQUESTS)
                else -> {
                    println(response.status.value)
                    Result.Error(ResponseError.UNKNOWN)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ResponseError.UNKNOWN)
        }

    }

    override suspend fun fetchMovieProviders(id: Int): Result<Country, ResponseError> {

        return try {

            val response = client.get(MOVIE_WATCH_PROVIDERS_ENDPOINT + id + "/watch/providers")
            val body = response.body<MovieWatchProvidersResponse>().results[region]

            when (response.status.value) {
                200 -> {
                    if (body != null) {
                        Result.Success(body)
                    } else {
                        Result.Error(ResponseError.NULL_VALUE)
                    }
                }

                400 -> Result.Error(ResponseError.BAD_REQUEST)
                401 -> Result.Error(ResponseError.UNAUTHORIZED)
                403 -> Result.Error(ResponseError.FORBIDDEN)
                404 -> Result.Error(ResponseError.NOT_FOUND)
                405 -> Result.Error(ResponseError.METHOD_NOT_ALLOWED)
                408 -> Result.Error(ResponseError.REQUEST_TIMEOUT)
                429 -> Result.Error(ResponseError.TOO_MANY_REQUESTS)
                else -> {
                    println(response.status.value)
                    Result.Error(ResponseError.UNKNOWN)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ResponseError.UNKNOWN)
        }

    }

    override suspend fun fetchTvShowWatchProviders(id: Int): Result<Country, ResponseError> {

        return try {

            val response = client.get("$TV_SHOW_DETAILS_ENDPOINT$id/watch/providers")
            val body = response.body<MovieWatchProvidersResponse>().results[region]

            when (response.status.value) {
                200 -> {
                    if (body != null) {
                        Result.Success(body)
                    } else {
                        Result.Error(ResponseError.NULL_VALUE)
                    }
                }

                400 -> Result.Error(ResponseError.BAD_REQUEST)
                401 -> Result.Error(ResponseError.UNAUTHORIZED)
                403 -> Result.Error(ResponseError.FORBIDDEN)
                404 -> Result.Error(ResponseError.NOT_FOUND)
                405 -> Result.Error(ResponseError.METHOD_NOT_ALLOWED)
                408 -> Result.Error(ResponseError.REQUEST_TIMEOUT)
                429 -> Result.Error(ResponseError.TOO_MANY_REQUESTS)

                else -> {
                    println(response.status.value)
                    Result.Error(ResponseError.UNKNOWN)
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ResponseError.UNKNOWN)
        }

    }

    override suspend fun search(query: String): Result<ApiResponse, ResponseError> {

        return try {

            println("Query -> $query")
            val response = client.get(SEARCH_ENDPOINT + "query=$query&language=$languageTag")

            when (response.status.value) {
                200 -> Result.Success(response.body<ApiResponse>())
                400 -> Result.Error(ResponseError.BAD_REQUEST)
                401 -> Result.Error(ResponseError.UNAUTHORIZED)
                403 -> Result.Error(ResponseError.FORBIDDEN)
                404 -> Result.Error(ResponseError.NOT_FOUND)
                405 -> Result.Error(ResponseError.METHOD_NOT_ALLOWED)
                408 -> Result.Error(ResponseError.REQUEST_TIMEOUT)
                429 -> Result.Error(ResponseError.TOO_MANY_REQUESTS)
                else -> {
                    println(response.status.value)
                    Result.Error(ResponseError.UNKNOWN)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ResponseError.UNKNOWN)
        }

    }

}
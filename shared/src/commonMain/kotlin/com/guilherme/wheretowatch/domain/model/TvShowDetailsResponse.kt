package com.guilherme.wheretowatch.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowDetailsResponse(

    @SerialName("id")
    val id: Int,

    @SerialName("poster_path")
    val posterPath: String = "",

    @SerialName("original_name")
    val originalName: String = "",

    @SerialName("name")
    val name: String = "",

    @SerialName("overview")
    val overview: String = "",

    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int = 0,

    @SerialName("number_of_seasons")
    val numberOfSeasons: Int = 0,

    @SerialName("first_air_date")
    val firstAirDate: String = "",

    @SerialName("last_air_date")
    val lastAirDate: String = "",

    @SerialName("status")
    val status: String = "",

    @SerialName("vote_average")
    val voteAverage: Float = 0f,

    @SerialName("vote_count")
    val voteCount: Int = 0

)

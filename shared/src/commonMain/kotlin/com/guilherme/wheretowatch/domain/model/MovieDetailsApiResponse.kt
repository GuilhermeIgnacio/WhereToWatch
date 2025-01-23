package com.guilherme.wheretowatch.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieDetailsResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("title")
    val title: String,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("runtime")
    val runtime: Int,

    @SerialName("vote_average")
    val voteAverage: Float,

    @SerialName("vote_count")
    val voteCount: Int
)
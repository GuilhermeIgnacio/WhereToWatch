package com.guilherme.wheretowatch.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val results: List<MovieData>
)

@Serializable
data class MovieData(
    @SerialName("id")
    val id: Int,

    @SerialName("poster_path")
    val posterPath: String? = null
)



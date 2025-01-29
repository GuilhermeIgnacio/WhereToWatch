package com.guilherme.wheretowatch.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val results: List<MediaData>
)

@Serializable
data class MediaData(
    @SerialName("id")
    val id: Int,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("media_type")
    val mediaType: String? = null
)



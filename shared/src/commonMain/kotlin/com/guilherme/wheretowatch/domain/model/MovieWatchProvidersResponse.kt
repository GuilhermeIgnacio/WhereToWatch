package com.guilherme.wheretowatch.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieWatchProvidersResponse(
    @SerialName("results")
    val results: Map<String, Country>
)

@Serializable
data class Country(
    @SerialName("link")
    val link: String? = null,

    @SerialName("flatrate")
    val flatrate: List<Provider>? = null,

    @SerialName("buy")
    val buy: List<Provider>? = null,

    @SerialName("rent")
    val rent: List<Provider>? = null,

    @SerialName("ads")
    val ads: List<Provider>? = null,
)

@Serializable
data class Provider(
    @SerialName("logo_path")
    val logoPath: String,

    @SerialName("provider_id")
    val providerId: Int,

    @SerialName("provider_name")
    val providerName: String,

    @SerialName("display_priority")
    val displayPriority: Int
)
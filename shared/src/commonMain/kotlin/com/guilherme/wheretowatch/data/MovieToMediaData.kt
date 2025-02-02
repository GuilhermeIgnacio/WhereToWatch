package com.guilherme.wheretowatch.data

import com.guilherme.wheretowatch.domain.MediaType
import com.guilherme.wheretowatch.domain.model.MediaData
import com.guilherme.wheretowatch.domain.model.MovieDetailsResponse

fun MovieDetailsResponse.toMovieData(): MediaData {
    return MediaData(
        id = id,
        posterPath = posterPath,
        mediaType = MediaType.MOVIE.value
    )
}
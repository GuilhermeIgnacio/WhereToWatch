package com.guilherme.wheretowatch.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.guilherme.Database
import com.guilherme.wheretowatch.domain.model.MediaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DriverFactory {
    fun createDriver(): SqlDriver
}

class LocalDatabase(
    driverFactory: DriverFactory
) {
    private val database = Database(driverFactory.createDriver())
    private val query = database.bookmarkQueries

    fun getBookmarks(): Flow<List<MediaData>> {
        return query.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map {bookmarkEntities ->
                bookmarkEntities.map { bookmarkEntity ->
                    MediaData(
                        id = bookmarkEntity.id.toInt(),
                        posterPath = bookmarkEntity.posterPath,
                        mediaType = bookmarkEntity.mediaType
                    )
                }
        }
    }

    fun insert(media: MediaData) {
        query.insert(
            id = media.id.toLong(),
            posterPath = media.posterPath!!,
            mediaType = media.mediaType!!
        )
    }

    fun delete(media: MediaData) {
        query.delete(media.id.toLong())
    }

}
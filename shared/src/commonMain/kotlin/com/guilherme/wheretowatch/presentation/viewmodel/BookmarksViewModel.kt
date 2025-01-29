package com.guilherme.wheretowatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.wheretowatch.data.local.LocalDatabase
import com.guilherme.wheretowatch.domain.model.MediaData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class BookmarksState(
    val bookmarks: List<MediaData> = emptyList(),
)

class BookmarksViewModel(private val database: LocalDatabase) : ViewModel() {

    private val _state = MutableStateFlow(BookmarksState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            database.getBookmarks().collect { list ->
                _state.update {
                    it.copy(
                        bookmarks = list
                    )
                }
            }


        }
    }

}
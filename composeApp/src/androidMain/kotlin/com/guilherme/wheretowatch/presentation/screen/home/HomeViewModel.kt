package com.guilherme.wheretowatch.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import kotlinx.coroutines.launch

class HomeViewModel(
    private val tmdbApiService: TheMovieDatabaseApiService
): ViewModel() {

    fun lorem() {
        viewModelScope.launch {
            tmdbApiService.fetchMovies()
        }
    }

}
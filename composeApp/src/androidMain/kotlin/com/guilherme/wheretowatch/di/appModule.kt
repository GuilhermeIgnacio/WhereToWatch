package com.guilherme.wheretowatch.di

import com.guilherme.wheretowatch.data.remote.api.TheMovieDatabaseApiServiceImpl
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.presentation.screen.home.HomeViewModel
import com.guilherme.wheretowatch.presentation.screen.moviedetails.MovieDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TheMovieDatabaseApiService> { TheMovieDatabaseApiServiceImpl() }
    viewModel { HomeViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}
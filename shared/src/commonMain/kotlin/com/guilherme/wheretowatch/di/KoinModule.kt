package com.guilherme.wheretowatch.di

import com.guilherme.wheretowatch.data.local.LocalDatabase
import com.guilherme.wheretowatch.data.remote.api.TheMovieDatabaseApiServiceImpl
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import com.guilherme.wheretowatch.presentation.viewmodel.BookmarksViewModel
import com.guilherme.wheretowatch.presentation.viewmodel.HomeViewModel
import com.guilherme.wheretowatch.presentation.viewmodel.MovieDetailsViewModel
import com.guilherme.wheretowatch.presentation.viewmodel.TvShowDetailsViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val targetModule: Module

val commonModules = module {
    single<TheMovieDatabaseApiService> { TheMovieDatabaseApiServiceImpl() }
    single { LocalDatabase(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { MovieDetailsViewModel(get(), get()) }
    viewModel { TvShowDetailsViewModel(get()) }
    viewModel { BookmarksViewModel(get()) }
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, commonModules)
    }
}
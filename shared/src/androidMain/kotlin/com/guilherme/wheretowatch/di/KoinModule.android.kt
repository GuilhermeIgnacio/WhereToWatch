package com.guilherme.wheretowatch.di

import com.guilherme.wheretowatch.data.local.AndroidDriverFactory
import com.guilherme.wheretowatch.data.local.DriverFactory
import com.guilherme.wheretowatch.presentation.viewmodel.HomeViewModel
import com.guilherme.wheretowatch.presentation.viewmodel.MovieDetailsViewModel
import com.guilherme.wheretowatch.presentation.viewmodel.TvShowDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

actual val targetModule = module {
    single<DriverFactory> { AndroidDriverFactory(androidContext()) }
}
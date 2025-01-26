package com.guilherme.wheretowatch.di

import com.guilherme.wheretowatch.data.remote.api.TheMovieDatabaseApiServiceImpl
import com.guilherme.wheretowatch.domain.TheMovieDatabaseApiService
import org.koin.dsl.module

val appModule = module {
    single<TheMovieDatabaseApiService> { TheMovieDatabaseApiServiceImpl() }

}
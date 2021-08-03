package com.dicoding.justview.di

import com.dicoding.justview.core.domain.usecase.ViewInteractor
import com.dicoding.justview.core.domain.usecase.ViewUseCase
import com.dicoding.justview.detail.DetailViewModel
import com.dicoding.justview.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ViewUseCase> {
        ViewInteractor(get())
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
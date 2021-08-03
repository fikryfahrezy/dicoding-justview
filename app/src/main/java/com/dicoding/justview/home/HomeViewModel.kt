package com.dicoding.justview.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.justview.core.domain.usecase.ViewUseCase

class HomeViewModel(viewUseCase: ViewUseCase) : ViewModel() {
    val allView = viewUseCase.getAllView().asLiveData()
}
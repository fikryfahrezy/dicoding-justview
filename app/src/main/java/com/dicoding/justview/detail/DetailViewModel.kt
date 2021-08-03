package com.dicoding.justview.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.justview.core.domain.model.View
import com.dicoding.justview.core.domain.usecase.ViewUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val viewUseCase: ViewUseCase) : ViewModel() {

    fun getOneView(identifier: String) = viewUseCase.getOneView(identifier).asLiveData()

    fun setFavoriteView(data: View, status: Boolean) {
        viewModelScope.launch {
            viewUseCase.setFavoriteView(data, status)
        }
    }
}
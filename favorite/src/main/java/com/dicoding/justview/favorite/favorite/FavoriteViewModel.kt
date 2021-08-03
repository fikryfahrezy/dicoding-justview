package com.dicoding.justview.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.justview.core.domain.model.View
import com.dicoding.justview.core.domain.usecase.ViewUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteViewModel(private val viewUseCase: ViewUseCase) : ViewModel() {

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val favoriteViews =
        queryChannel
            .asFlow()
            .debounce(300)
            .mapLatest {
                viewUseCase.getFavoriteViews(it).first()
            }
            .asLiveData()

    fun setFavoriteView(data: View) {
        viewModelScope.launch {
            viewUseCase.setFavoriteView(data)
        }
    }
}

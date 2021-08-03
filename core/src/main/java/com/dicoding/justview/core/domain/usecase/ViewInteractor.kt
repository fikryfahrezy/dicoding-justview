package com.dicoding.justview.core.domain.usecase

import com.dicoding.justview.core.data.Resource
import com.dicoding.justview.core.domain.model.View
import com.dicoding.justview.core.domain.repository.IViewRepository
import kotlinx.coroutines.flow.Flow

class ViewInteractor(private val viewRepository: IViewRepository) : ViewUseCase {

    override fun getAllView(): Flow<Resource<List<View>>> = viewRepository.getAllView()

    override fun getOneView(identifier: String): Flow<View?> = viewRepository.getOneView(identifier)

    override fun getFavoriteViews(search: String): Flow<List<View>> =
        viewRepository.getFavoriteViews(search)

    override suspend fun setFavoriteView(data: View, status: Boolean?) =
        viewRepository.setFavoriteView(data, status)
}
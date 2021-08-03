package com.dicoding.justview.core.domain.repository

import com.dicoding.justview.core.data.Resource
import com.dicoding.justview.core.domain.model.View
import kotlinx.coroutines.flow.Flow

interface IViewRepository {

    fun getAllView(): Flow<Resource<List<View>>>

    fun getOneView(identifier: String): Flow<View?>

    fun getFavoriteViews(search: String): Flow<List<View>>

    suspend fun setFavoriteView(data: View, status: Boolean? = null)
}
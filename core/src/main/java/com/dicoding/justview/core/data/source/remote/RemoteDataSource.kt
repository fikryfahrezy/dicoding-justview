package com.dicoding.justview.core.data.source.remote

import com.dicoding.justview.core.data.source.remote.network.ApiResponse
import com.dicoding.justview.core.data.source.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getViews() = flow {
        try {
            val data = apiService.getViews().data.result
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(data))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            val msg = e.toString()
            emit(ApiResponse.Error(msg))
            Timber.e(msg)
        }
    }.flowOn(Dispatchers.IO)
}
package com.dicoding.justview.core.data

import com.dicoding.justview.core.data.source.local.LocalDataSource
import com.dicoding.justview.core.data.source.remote.RemoteDataSource
import com.dicoding.justview.core.data.source.remote.network.ApiResponse
import com.dicoding.justview.core.data.source.remote.response.ViewResponse
import com.dicoding.justview.core.domain.model.View
import com.dicoding.justview.core.domain.repository.IViewRepository
import com.dicoding.justview.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ViewRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IViewRepository {
    override fun getAllView(): Flow<Resource<List<View>>> =
        object : NetworkBoundResource<List<View>, List<ViewResponse>>() {
            override fun loadFromDB(): Flow<List<View>> = localDataSource.getAllView().map {
                DataMapper.mapEntitiesToDomain(it)
            }


            override fun shouldFetch(data: List<View>?): Boolean {
                return data == null || data.isEmpty()
//                return true // always fetch form the remote
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ViewResponse>>> =
                remoteDataSource.getViews()

            override suspend fun saveCallResult(data: List<ViewResponse>) {
                val mappedData = DataMapper.mapResponsesToEntities(data)
                localDataSource.bulkInsertView(mappedData)
            }

        }.asFlow()

    override fun getOneView(identifier: String): Flow<View?> = flow {
        val dataEntity = localDataSource.getOneView(identifier)
        emit(
            DataMapper.mapEntityToDomain(
                dataEntity.firstOrNull()
            )
        )
    }

    override fun getFavoriteViews(search: String): Flow<List<View>> =
        localDataSource.getFavoriteViews("%$search%").map {
            DataMapper.mapEntitiesToDomain(it)
        }


    override suspend fun setFavoriteView(data: View, status: Boolean?) {
        val mappedData = DataMapper.mapDomainToEntity(data)
        localDataSource.setFavoriteView(mappedData, status)
    }
}
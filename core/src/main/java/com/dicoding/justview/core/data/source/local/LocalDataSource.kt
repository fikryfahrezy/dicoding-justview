package com.dicoding.justview.core.data.source.local

import com.dicoding.justview.core.data.source.local.entity.ViewEntity
import com.dicoding.justview.core.data.source.local.room.JvDatabaseDao

class LocalDataSource(private val jvDatabaseDao: JvDatabaseDao) {

    fun getAllView() = jvDatabaseDao.getAllView()

    fun getOneView(identifier: String) = jvDatabaseDao.getOneView(identifier)

    fun getFavoriteViews(search: String) = jvDatabaseDao.getFavoriteViews(search)

    suspend fun bulkInsertView(data: List<ViewEntity>) = jvDatabaseDao.bulkInsertView(data)

    suspend fun setFavoriteView(data: ViewEntity, status: Boolean? = null) {
        if (status != null)
            data.isFavorite = status
        else
            data.isFavorite = !data.isFavorite
        jvDatabaseDao.update(data)
    }
}
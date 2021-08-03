package com.dicoding.justview.core.data.source.local.room

import androidx.room.*
import com.dicoding.justview.core.data.source.local.entity.ViewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JvDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsertView(data: List<ViewEntity>)

    @Query("SELECT * FROM `view`")
    fun getAllView(): Flow<List<ViewEntity>>

    @Query("SELECT * FROM `view` WHERE viewId = :identifier")
    fun getOneView(identifier: String): Flow<ViewEntity?>

    @Update
    suspend fun update(viewEntity: ViewEntity)

    @Query("SELECT * FROM `view` WHERE isFavorite = 1 AND name LIKE :search")
    fun getFavoriteViews(search: String): Flow<List<ViewEntity>>
}
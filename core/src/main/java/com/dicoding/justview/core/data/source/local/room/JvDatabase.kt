package com.dicoding.justview.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.justview.core.data.source.local.entity.ViewEntity

@Database(entities = [ViewEntity::class], version = 1, exportSchema = false)
abstract class JvDatabase : RoomDatabase() {
    abstract fun jvDatabaseDao(): JvDatabaseDao
}
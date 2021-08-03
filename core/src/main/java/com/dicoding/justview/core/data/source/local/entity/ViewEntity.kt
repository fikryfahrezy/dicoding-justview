package com.dicoding.justview.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "view")
data class ViewEntity(
    @PrimaryKey
    @NonNull
    var viewId: String,
    var name: String,
    var image: String,
    var width: Int,
    var height: Int,
    var source: String,
    var sourceLink: String,
    var lat: Float,
    var lng: Float,
    var isFavorite: Boolean = false,
)

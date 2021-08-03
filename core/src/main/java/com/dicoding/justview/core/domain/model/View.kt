package com.dicoding.justview.core.domain.model

data class View(
    val viewId: String,
    val name: String,
    val image: String,
    val width: Int,
    val height: Int,
    val source: String,
    val sourceLink: String,
    val lat: Float,
    val lng: Float,
    val isFavorite: Boolean,
)
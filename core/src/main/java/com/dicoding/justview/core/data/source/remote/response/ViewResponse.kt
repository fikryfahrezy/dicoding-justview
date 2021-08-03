package com.dicoding.justview.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ViewResponse(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("width")
    val width: Int,

    @field:SerializedName("height")
    val height: Int,

    @field:SerializedName("source")
    val source: String,

    @field:SerializedName("source_link")
    val sourceLink: String,

    @field:SerializedName("lat")
    val lat: Float,

    @field:SerializedName("lng")
    val lng: Float,
)


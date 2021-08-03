package com.dicoding.justview.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListViewResult(

    @field:SerializedName("result")
    val result: List<ViewResponse>
)

data class ListViewResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: ListViewResult
)

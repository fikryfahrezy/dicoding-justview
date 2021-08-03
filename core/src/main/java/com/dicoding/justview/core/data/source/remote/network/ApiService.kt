package com.dicoding.justview.core.data.source.remote.network

import com.dicoding.justview.core.data.source.remote.response.ListViewResponse
import retrofit2.http.GET

interface ApiService {
    @GET("views?t=view")
    suspend fun getViews(): ListViewResponse
}

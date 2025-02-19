package com.emil.data.network

import com.emil.data.model.VideoResponse
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("/vk-test")
    suspend fun getVideo (): Response<List<VideoResponse>>
}
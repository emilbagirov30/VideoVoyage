package com.emil.data.repository

import com.emil.data.model.toDomainModelList
import com.emil.data.network.RetrofitInstance
import com.emil.domain.model.Video
import com.emil.domain.repository.VideoRepository

class VideoRepositoryImpl : VideoRepository  {
    override suspend fun getVideo(): List<Video>? {

       val response = RetrofitInstance.apiService.getVideo()

        return if (response.isSuccessful)
            response.body()?.toDomainModelList()
        else null
    }

}
package com.emil.data.repository


import com.emil.data.model.toDomainModelList
import com.emil.data.model.toLocalDbModel
import com.emil.domain.model.VideoCache
import com.emil.domain.repository.CacheRepository

class CacheRepositoryImpl (private val videoDao:VideoDao): CacheRepository {

    override suspend fun cacheVideo(video: VideoCache) {
       videoDao.cacheVideo(video.toLocalDbModel())
    }

    override suspend fun getVideoFromCache(): List<VideoCache> {
        return videoDao.getAllVideo().toDomainModelList()
    }

}
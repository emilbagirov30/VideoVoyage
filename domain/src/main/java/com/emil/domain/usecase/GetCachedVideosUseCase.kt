package com.emil.domain.usecase

import com.emil.domain.model.VideoCache
import com.emil.domain.repository.CacheRepository

class GetCachedVideosUseCase (private val cacheRepository: CacheRepository){
    suspend fun execute ():List<VideoCache>{
        return cacheRepository.getVideoFromCache()
    }
}
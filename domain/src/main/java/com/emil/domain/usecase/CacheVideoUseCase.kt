package com.emil.domain.usecase


import com.emil.domain.model.VideoCache
import com.emil.domain.repository.CacheRepository

class CacheVideoUseCase (private val cacheRepository: CacheRepository) {
    suspend fun execute (video: VideoCache){
            cacheRepository.cacheVideo(video)
    }
}
package com.emil.domain.usecase

import com.emil.domain.model.Video
import com.emil.domain.repository.VideoRepository

class GetVideoUseCase (private val videoRepository: VideoRepository) {
    suspend fun execute (): List<Video>{
        return  videoRepository.getVideo()
    }
}
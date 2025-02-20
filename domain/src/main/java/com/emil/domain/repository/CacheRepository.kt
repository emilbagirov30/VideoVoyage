package com.emil.domain.repository

import com.emil.domain.model.Video
import com.emil.domain.model.VideoCache

interface CacheRepository {
    suspend fun cacheVideo (video:VideoCache)
    suspend fun getVideoFromCache ():List<VideoCache>
}

package com.emil.domain.repository

import com.emil.domain.model.Video

interface VideoRepository {
    suspend fun getVideo(): List<Video>
}
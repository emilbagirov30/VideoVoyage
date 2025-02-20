package com.emil.domain.usecase

import com.emil.domain.model.Video
import com.emil.domain.repository.VideoRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetVideoUseCaseTest {
    private val videoRepository = mock<VideoRepository>()
    private val getVideoUseCase = GetVideoUseCase(videoRepository)

    @Test
    fun `execute should return videos from repository`() = runBlocking {
        val expectedVideos = listOf(Video("video1","url1",1), Video("video2","url2",2))
        Mockito.`when`(videoRepository.getVideo()).thenReturn(expectedVideos)
        val actualVideos = getVideoUseCase.execute()
        Assertions.assertEquals(expectedVideos, actualVideos)
    }

    @Test
    fun `execute should return null when repository returns null`() = runBlocking {
        Mockito.`when`(videoRepository.getVideo()).thenReturn(null)
        val actualVideos = getVideoUseCase.execute()
        Assertions.assertNull(actualVideos)
    }
}

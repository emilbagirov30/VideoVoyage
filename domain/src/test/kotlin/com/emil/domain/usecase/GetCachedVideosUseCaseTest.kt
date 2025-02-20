package com.emil.domain.usecase



import com.emil.domain.model.VideoCache
import com.emil.domain.repository.CacheRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito


class GetCachedVideosUseCaseTest {
   private val cacheRepository = mock<CacheRepository>()
   private val getCachedVideosUseCase = GetCachedVideosUseCase(cacheRepository)

   @Test
   fun `execute should return cached videos`() = runBlocking {
      val cachedVideos = listOf(VideoCache("testName", 55))
      Mockito.`when`(cacheRepository.getVideoFromCache()).thenReturn(cachedVideos)
      val actualVideos = getCachedVideosUseCase.execute()
      Assertions.assertEquals(cachedVideos, actualVideos)
   }
}
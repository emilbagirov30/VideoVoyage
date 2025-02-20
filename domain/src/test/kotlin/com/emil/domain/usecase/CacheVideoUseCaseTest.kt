package com.emil.domain.usecase

import com.emil.domain.model.VideoCache
import com.emil.domain.repository.CacheRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class CacheVideoUseCaseTest {
    private val cacheRepository = mock<CacheRepository>()
    private val cacheVideoUseCase = CacheVideoUseCase(cacheRepository)

    @Test
    fun `execute should call cacheVideo with correct parameter`() = runBlocking {
        val video = VideoCache("testName", 55)
        cacheVideoUseCase.execute(video)
        Mockito.verify(cacheRepository).cacheVideo(video)
    }
}

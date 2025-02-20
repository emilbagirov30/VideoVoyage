package presentation.viewmodel

import com.emil.domain.model.Video
import com.emil.domain.model.VideoCache
import com.emil.domain.model.toResponseModel
import com.emil.domain.usecase.CacheVideoUseCase
import com.emil.domain.usecase.GetCachedVideosUseCase
import com.emil.domain.usecase.GetVideoUseCase
import com.emil.videovoyage.presentation.viewmodel.VideoViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


class VideoViewModelTest {
    private val getVideoUseCase = mock<GetVideoUseCase>()
    private val cacheVideoUseCase = mock<CacheVideoUseCase>()
    private val getCachedVideosUseCase = mock<GetCachedVideosUseCase>()
    private lateinit var videoViewModel: VideoViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        videoViewModel = VideoViewModel(getVideoUseCase, cacheVideoUseCase, getCachedVideosUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `getVideoList should update videoList and call onSuccess when API returns data`() = runTest {
        val videos = listOf(Video("video1", "url1", 1))
        Mockito.`when`(getVideoUseCase.execute()).thenReturn(videos)
        val onSuccess = mock<() -> Unit>()
        val onError = mock<() -> Unit>()

        videoViewModel.getVideoList(onSuccess, onError)

        assertEquals(videos, videoViewModel.videoList.value)
        verify(onSuccess).invoke()
    }

    @Test
    fun `getVideoList should call onError and load cached videos when API returns null`() = runTest {
        Mockito.`when`(getVideoUseCase.execute()).thenReturn(null)
        val cachedVideos = listOf(VideoCache("cachedVideo1", 1))
        Mockito.`when`(getCachedVideosUseCase.execute()).thenReturn(cachedVideos)
        val onSuccess = mock<() -> Unit>()
        val onError = mock<() -> Unit>()

        videoViewModel.getVideoList(onSuccess, onError)

        verify(onError).invoke()
        assertEquals(cachedVideos.map { it.toResponseModel() }, videoViewModel.videoList.value)
    }

    @Test
    fun `getVideoList should handle exceptions and load cached videos`() = runTest {
        Mockito.`when`(getVideoUseCase.execute()).thenThrow(RuntimeException("API error"))
        val cachedVideos = listOf(VideoCache("cachedVideo1", 1))
        Mockito.`when`(getCachedVideosUseCase.execute()).thenReturn(cachedVideos)
        val onSuccess = mock<() -> Unit>()
        val onError = mock<() -> Unit>()

        videoViewModel.getVideoList(onSuccess, onError)

        verify(onError).invoke()
        assertEquals(cachedVideos.map { it.toResponseModel() }, videoViewModel.videoList.value)
    }
}

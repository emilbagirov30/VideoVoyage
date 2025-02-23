package com.emil.videovoyage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emil.domain.model.Video
import com.emil.domain.model.toCacheModel
import com.emil.domain.model.toResponseModelList
import com.emil.domain.usecase.CacheVideoUseCase
import com.emil.domain.usecase.GetCachedVideosUseCase
import com.emil.domain.usecase.GetVideoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel (private val getVideoUseCase: GetVideoUseCase,
                      private val cacheVideoUseCase: CacheVideoUseCase,
                      private val getCachedVideosUseCase: GetCachedVideosUseCase) : ViewModel () {

    private val _videoList = MutableStateFlow<List<Video>?>(null)
    val videoList: StateFlow<List<Video>?> = _videoList

    fun getVideoList(onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            runCatching { getVideoUseCase.execute() }.onSuccess { response ->
                if (response.isNullOrEmpty()) handleFailure(onError)
                 else {
                    _videoList.value = response
                    insertVideoList(response)
                    onSuccess()
                }
            }.onFailure {
                handleFailure(onError)
            }
        }
    }

    private fun handleFailure(onError: () -> Unit) {
        onError()
        getCachedVideos()
    }

    private fun insertVideoList(videos: List<Video>) {
        viewModelScope.launch(Dispatchers.IO) {
        videos.forEach { video ->
            cacheVideoUseCase.execute(video.toCacheModel())
         }
        }
    }

   private fun getCachedVideos(){
       viewModelScope.launch(Dispatchers.IO ){
          val result = getCachedVideosUseCase.execute()
           _videoList.emit(result.toResponseModelList())
       }
   }


}





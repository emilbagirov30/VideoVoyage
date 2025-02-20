package com.emil.videovoyage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emil.domain.model.Video
import com.emil.domain.model.VideoCache
import com.emil.domain.model.toCacheModel
import com.emil.domain.model.toResponseModelList
import com.emil.domain.usecase.CacheVideoUseCase
import com.emil.domain.usecase.GetCacheVideoUseCase
import com.emil.domain.usecase.GetVideoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel (private val getVideoUseCase: GetVideoUseCase,
                      private val cacheVideoUseCase: CacheVideoUseCase,
                      private val getCacheVideoUseCase: GetCacheVideoUseCase) : ViewModel () {

    private val _videoList = MutableStateFlow<List<Video>?>(null)
    val videoList: StateFlow<List<Video>?> = _videoList

    fun getVideoList(onSuccess:() -> Unit, onError:() -> Unit) {
        viewModelScope.launch {
            try {
                val response = getVideoUseCase.execute()
                if (response != null) {
                    _videoList.value = response
                    insertVideoList(response)
                    onSuccess()
                } else {
                    onError()
                    getCacheVideo()
                }
            }catch (e:Exception){
                onError()
                getCacheVideo()
            }
        }
    }


    private suspend fun insertVideoList(videos: List<Video>) {
        videos.forEach { video ->
            cacheVideoUseCase.execute(video.toCacheModel())
        }
    }

   private fun getCacheVideo(){
       viewModelScope.launch {
          val result = getCacheVideoUseCase.execute()
           _videoList.value = result.toResponseModelList()
       }
   }



}





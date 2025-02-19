package com.emil.videovoyage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emil.domain.model.Video
import com.emil.domain.usecase.GetVideoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel (private val getVideoUseCase: GetVideoUseCase) : ViewModel () {

    private val _videoList = MutableStateFlow<List<Video>?>(null)
    val videoList: StateFlow<List<Video>?> = _videoList

    fun getVideoList(onSuccess:() -> Unit, onError:() -> Unit) {
        viewModelScope.launch {
            try {
                val response = getVideoUseCase.execute()
                if (response != null) {
                    _videoList.value = response
                    onSuccess()
                } else onError()
            }catch (e:Exception){
                onError()
            }
        }
    }


}





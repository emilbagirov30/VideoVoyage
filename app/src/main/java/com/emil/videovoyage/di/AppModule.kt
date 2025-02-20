package com.emil.videovoyage.di

import com.emil.videovoyage.presentation.viewmodel.VideoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel <VideoViewModel> {
        added tests for use cases and viewmodel        VideoViewModel(getVideoUseCase = get(), cacheVideoUseCase = get(), getCachedVideosUseCase = get())
    }

}
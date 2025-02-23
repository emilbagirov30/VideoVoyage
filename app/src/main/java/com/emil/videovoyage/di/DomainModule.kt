package com.emil.videovoyage.di

import com.emil.domain.usecase.CacheVideoUseCase
import com.emil.domain.usecase.GetCachedVideosUseCase
import com.emil.domain.usecase.GetVideoUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetVideoUseCase>{
       GetVideoUseCase(videoRepository = get())
    }

    factory<CacheVideoUseCase>{
       CacheVideoUseCase(cacheRepository = get())
    }
    factory<GetCachedVideosUseCase>{
        GetCachedVideosUseCase(cacheRepository = get())
    }

}
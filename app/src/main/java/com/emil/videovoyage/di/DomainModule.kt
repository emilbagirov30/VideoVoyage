package com.emil.videovoyage.di

import com.emil.domain.usecase.GetVideoUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetVideoUseCase>{
       GetVideoUseCase(videoRepository = get())
    }

}
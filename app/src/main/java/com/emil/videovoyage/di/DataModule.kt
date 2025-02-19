package com.emil.videovoyage.di

import com.emil.data.repository.VideoRepositoryImpl
import com.emil.domain.repository.VideoRepository
import org.koin.dsl.module

val dataModule = module {

    single<VideoRepository>{
        VideoRepositoryImpl()
    }

}
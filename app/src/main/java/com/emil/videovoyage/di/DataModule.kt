package com.emil.videovoyage.di

import androidx.room.Room
import com.emil.data.repository.AppDatabase
import com.emil.data.repository.CacheRepositoryImpl
import com.emil.data.repository.VideoDao
import com.emil.data.repository.VideoRepositoryImpl
import com.emil.domain.repository.CacheRepository
import com.emil.domain.repository.VideoRepository
import org.koin.dsl.module

val dataModule = module {

    single<VideoRepository>{
        VideoRepositoryImpl()
    }
    single<CacheRepository>{
       CacheRepositoryImpl(videoDao = get())
    }
    single<VideoDao> {
        get<AppDatabase>().videoDao()
    }
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "appLocalDb").build()
    }
}